package com.bitwormhole.passwordgm.data.repositories;

import android.content.Context;

import com.bitwormhole.passwordgm.data.ids.RepositoryAlias;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.security.KeyPairHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
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
    public RepositoryHolder get(KeyPair kp) {
        RepositoryParams p2 = prepareParams(mContext, kp);
        checkParams(p2);
        return new MyHolder(p2);
    }

    @Override
    public RepositoryHolder getRoot() {
        KeyPairManager kpm = KeyPairManager.Agent.getKeyPairManager();
        KeyPairHolder kph = kpm.getRoot();
        if (!kph.exists()) {
            kph.create();
        }
        return this.get(kph.fetch());
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

        if (alias == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: alias is null");
        }
        if (kp == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: keypair is null");
        }
        if (location == null) {
            throw new PGMException(PGMErrorCode.Unknown, "param: location is null");
        }
    }

    private static RepositoryParams prepareParams(Context ctx, KeyPair kp) {

        PublicKey pub = kp.getPublic();
        String sum = HashUtils.hexSum(pub.getEncoded(), HashUtils.MD5);
        Path base = ctx.getDataDir().toPath();
        Path location = base.resolve("repositories/" + sum);

        RepositoryParams params = new RepositoryParams();
        params.setAlias(new RepositoryAlias(sum));
        params.setLocation(location);
        params.setKeyPair(kp);
        return params;
    }
}
