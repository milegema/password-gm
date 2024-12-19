package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.io.IOException;

import javax.crypto.SecretKey;

public class RepositoryChecker {
    public void check(RepositoryContext ctx) throws IOException {

        this.checkSecretKey(ctx);
        this.checkConfig(ctx);

    }

    private void checkConfig(RepositoryContext ctx) {
        try {
            PropertyTable pt = ctx.getConfig().loadProperties();
            pt.size();

            // todo: more...
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkSecretKey(RepositoryContext ctx) {
        SecretKey sk = ctx.getSecretKeyManager().fetch();
        sk.getFormat();
        sk.getAlgorithm();

        // todo: more...

    }
}
