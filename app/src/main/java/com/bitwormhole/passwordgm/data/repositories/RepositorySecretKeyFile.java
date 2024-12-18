package com.bitwormhole.passwordgm.data.repositories;

import javax.crypto.SecretKey;

public class RepositorySecretKeyFile implements RepositorySecretKey {

    public RepositorySecretKeyFile(RepositoryContext ctx) {
    }

    @Override
    public boolean create() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public SecretKey fetch() {
        return null;
    }
}
