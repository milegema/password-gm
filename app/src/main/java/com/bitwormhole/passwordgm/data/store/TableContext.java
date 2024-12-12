package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.security.SecretKeyManager;

import java.nio.file.Path;

public class TableContext {


    private Path baseDir;
    private SecretKeyManager secretKeyManager;


    public TableContext() {
    }

    public Path getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(Path baseDir) {
        this.baseDir = baseDir;
    }

    public SecretKeyManager getSecretKeyManager() {
        return secretKeyManager;
    }

    public void setSecretKeyManager(SecretKeyManager secretKeyManager) {
        this.secretKeyManager = secretKeyManager;
    }
}
