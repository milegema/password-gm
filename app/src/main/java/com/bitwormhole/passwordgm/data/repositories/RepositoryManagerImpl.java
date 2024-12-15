package com.bitwormhole.passwordgm.data.repositories;

import android.content.Context;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.RepositoryAlias;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.utils.HashUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PublicKey;

final class RepositoryManagerImpl extends RepositoryManager {

    private final Context mContext;

    public RepositoryManagerImpl(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public RepositoryHolder get(RepositoryParams params) {
        RepositoryParams p2 = prepareParams(mContext, params);
        checkParams(p2);
        return new MyHolder(p2);
    }

    private static class MyHolder implements RepositoryHolder {

        private final RepositoryParams mParams;
        private Repository mCached;

        MyHolder(RepositoryParams rp) {
            this.mParams = rp;
        }

        @Override
        public RepositoryAlias alias() {
            return this.mParams.getAlias();
        }

        @Override
        public Repository fetch() {
            Repository repo = mCached;
            if (repo == null) {
                repo = RepositoryFactory.open(this.mParams);
                mCached = repo;
            }
            return repo;
        }

        @Override
        public Repository create() {
            Repository repo = RepositoryFactory.init(this.mParams);
            mCached = repo;
            return repo;
        }

        @Override
        public boolean exists() {
            return Files.exists(this.mParams.getLocation());
        }

        @Override
        public boolean delete() {
            // return false;
            throw new RuntimeException("no impl");
        }
    }

    private static void checkParams(RepositoryParams rp) {

        RepositoryAlias alias = rp.getAlias();
        KeyPair kp = rp.getKeyPair();
        Path location = rp.getLocation();
        ContextScope scope = rp.getScope();

        if (alias == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: alias is null");
        }
        if (kp == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: keypair is null");
        }
        if (location == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: location is null");
        }
        if (scope == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: scope is null");
        }
    }

    private static RepositoryParams prepareParams(Context ctx, RepositoryParams rp) {

        ContextScope scope = rp.getScope();
        PublicKey pub = rp.getKeyPair().getPublic();
        String sum = HashUtils.hexSum(pub.getEncoded(), HashUtils.SHA256);
        String path = "/tmp/repository";

        if (scope != null) {
            switch (scope) {
                case USER:
                    path = "/home/" + sum + "/repository";
                    break;
                case APP:
                case ROOT:
                    path = "/root/repository";
                    break;
                default:
            }
        }

        Path base = ctx.getDataDir().toPath();
        Path location = base.resolve("." + path);

        rp.setLocation(location);
        rp.setAlias(new RepositoryAlias(sum));
        return rp;
    }
}
