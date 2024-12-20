package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.ids.RepositoryAlias;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectManagerImpl;
import com.bitwormhole.passwordgm.data.repositories.refs.RefManagerImpl;
import com.bitwormhole.passwordgm.data.repositories.tables.TableManagerImpl;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class RepositoryFactory {

    /****
     * 打开已存在的仓库
     * */
    public static Repository open(RepositoryParams params) {

        RepositoryLayout layout = RepositoryLocator.locate(params.getLocation());
        Builder builder = new Builder();
        builder.init(params, layout);
        RepositoryContext ctx = builder.create();
        Repository repo = ctx.getRepository();

        // check repo
        try {
            RepositoryChecker repo_checker = new RepositoryChecker();
            repo_checker.check(ctx);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return repo;
    }

    /**
     * 创建并初始化新的仓库
     */
    public static Repository create(RepositoryParams params) {

        Path dir = params.getLocation();
        if (Files.exists(dir)) {
            String msg = "location of new repository is not empty, at " + dir;
            throw new PGMException(PGMErrorCode.Unknown, msg);
        }


        final String name_want = RepositoryLayout.REGULAR_REPO_FOLDER_NAME;
        final String name_have = dir.getFileName().toString();
        if (!name_want.equals(name_have)) {
            dir = dir.resolve(name_want);
        }

        RepositoryLayout.Builder b = new RepositoryLayout.Builder();
        b.setDotPasswordGM(dir);
        RepositoryLayout layout = b.create();

        Builder builder = new Builder();
        builder.init(params, layout);
        RepositoryContext ctx = builder.create();

        // init & check repo
        try {
            RepositoryInitializer repo_init = new RepositoryInitializer();
            RepositoryChecker repo_checker = new RepositoryChecker();
            repo_init.initial(ctx);
            repo_checker.check(ctx);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }

        return ctx.getRepository();
    }

    private static class Builder {

        final RepositoryContext context;

        public Builder() {
            this.context = new RepositoryContext();
        }

        public void init(RepositoryParams params, RepositoryLayout layout) {

            KeyPair kp = params.getKeyPair();
            RepositoryAlias alias = params.getAlias();

            if (alias == null) {
                throw new PGMException("no required param: alias");
            }
            if (kp == null) {
                throw new PGMException("no required param: keypair");
            }
            if (layout == null) {
                throw new PGMException("no required param: layout");
            }

            Path location = layout.getRepository();
            if (location == null) {
                throw new PGMException("no required param: location");
            }

            this.context.setLayout(layout);
            this.context.setAlias(alias);
            this.context.setKeyPair(kp);
            this.context.setLocation(location);
        }

        public RepositoryContext create() {
            RepositoryContext ctx = this.context;

            // set in init()
            // ctx.setKeyPair(null);
            // ctx.setLayout(null);
            // ctx.setLocation(null);
            // ctx.setAlias(null);


            ctx.setSecretKey(null);
            ctx.setSecretKeyManager(new RepositoryKeyFile(ctx));

            ctx.setConfig(new RepositoryConfigFile(ctx));
            ctx.setObjectManager(new ObjectManagerImpl(ctx));
            ctx.setRefManager(new RefManagerImpl(ctx));
            ctx.setTableManager(new TableManagerImpl(ctx));

            ctx.setRepository(new RepositoryImpl(ctx));

            return ctx;
        }
    }
}
