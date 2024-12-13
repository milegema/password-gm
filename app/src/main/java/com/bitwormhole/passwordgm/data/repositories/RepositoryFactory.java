package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;

import java.nio.file.Files;
import java.nio.file.Path;

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
     * */
    public static Repository init(RepositoryParams params) {

        Path dir = params.getLocation();
        if (Files.exists(dir)) {
            String msg = "location of new repository is not empty, at " + dir;
            throw new PGMException(PGMErrorCode.Unknown, msg);
        }

        RepositoryLayout.Builder b = new RepositoryLayout.Builder();
        b.setDotPGM(dir.resolve(".pgm"));
        RepositoryLayout l = b.create();


        return open(params);
    }
}
