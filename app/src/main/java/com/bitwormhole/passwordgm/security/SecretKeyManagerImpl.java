package com.bitwormhole.passwordgm.security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public class SecretKeyManagerImpl implements SecretKeyManager {

    private KeyPairManager keyPairManager;
    private Path secretKeysFolder;

    public SecretKeyManagerImpl() {
    }


    private static class MySecretKeyHolder implements SecretKeyHolder {

        private final KeyPair pair;
        private final String alias;
        private final Path file;

        public MySecretKeyHolder(KeyPairHolder kph, Path f) {
            this.pair = kph.fetch();
            this.alias = kph.alias();
            this.file = f;
        }

        @Override
        public boolean create() {
            return false;
        }

        @Override
        public boolean delete() {
            try {
                Files.delete(this.file);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean exists() {
            return Files.exists(this.file);
        }

        @Override
        public SecretKey fetch() {
            return null;
        }
    }

    public Path getSecretKeysFolder() {
        return secretKeysFolder;
    }

    public void setSecretKeysFolder(Path secretKeysFolder) {
        this.secretKeysFolder = secretKeysFolder;
    }

    public KeyPairManager getKeyPairManager() {
        return keyPairManager;
    }

    public void setKeyPairManager(KeyPairManager keyPairManager) {
        this.keyPairManager = keyPairManager;
    }

    @Override
    public SecretKeyHolder get(KeySelector sel) {
        KeyPairHolder kph = this.keyPairManager.get(sel);
        String alias = kph.alias();
        Path f = this.secretKeysFolder.resolve(alias);
        return new MySecretKeyHolder(kph, f);
    }
}
