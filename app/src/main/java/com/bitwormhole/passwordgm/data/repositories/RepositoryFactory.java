package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class RepositoryFactory {

    /****
     * 打开已存在的仓库
     * */
    public static Repository open(RepositoryParams params) {

        RepositoryLayout layout = RepositoryLocator.locate(params.getLocation());
        RepositoryContext ctx = new RepositoryContext();

        ctx.setRepository(new RepositoryImpl(ctx));
        ctx.setLayout(layout);
        ctx.setKeyPair(params.getKeyPair());
        ctx.setScope(params.getScope());
        ctx.setSecretKey(null);

        return ctx.getRepository();
    }

    /**
     * 创建并初始化新的仓库
     */
    public static Repository init(RepositoryParams params) {

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
        RepositoryLayout l = b.create();
        try {
            RepositoryInit repo_init = new RepositoryInit();
            repo_init.setKeyPair(params.getKeyPair());
            repo_init.init(l);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }

        return open(params);
    }
}
