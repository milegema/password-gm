package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecretKeyManagerImpl implements SecretKeyManager {

    private KeyPairManager keyPairManager;
    private Path secretKeysFolder;

    public SecretKeyManagerImpl() {
    }

    private static class MySecretKeyBuilder {

        public int size;
        public String algorithm;

        public SecretKey generate() throws NoSuchAlgorithmException {
            KeyGenerator kg = KeyGenerator.getInstance(this.algorithm);
            kg.init(this.size);
            return kg.generateKey();
        }
    }

    private static class MySecretKeyHolder implements SecretKeyHolder {

        private final String alias;
        private final Path file;
        private final KeyPairHolder kph;
        private KeyPair pair;
        private SecretKey cached;

        public MySecretKeyHolder(KeyPairHolder h, Path f) {
            this.kph = h;
            this.alias = kph.alias();
            this.file = f;
        }


        private KeyPair fetchKeyPair(boolean create) {
            KeyPair kp = this.pair;
            if (kp == null) {
                if (!kph.exists()) {
                    if (create) {
                        kph.create();
                    }
                }
                kp = kph.fetch();
                this.pair = kp;
            }
            return kp;
        }

        @Override
        public String alias() {
            return this.alias;
        }

        @Override
        public boolean create() {

            if (this.exists()) {
                return false;
            }

            // make key-pair
            KeyPair kp = this.fetchKeyPair(true);


            // make secret-key
            MySecretKeyBuilder skBuilder = new MySecretKeyBuilder();
            skBuilder.size = 256;
            skBuilder.algorithm = "AES";
            SecretKey sk;
            try {
                sk = skBuilder.generate();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            // make file-head
            SecretKeyFile.Head head = new SecretKeyFile.Head();
            head.outerAlgorithm = kp.getPublic().getAlgorithm();
            head.outerPadding = PaddingMode.PKCS1Padding;
            head.outerMode = CipherMode.NONE;
            head.innerAlgorithm = sk.getAlgorithm();
            head.innerContentType = "binary/aes-secret-key";

            // store
            SecretKeyFile skf = new SecretKeyFile();
            skf.setAlias(this.alias);
            skf.setFile(this.file);
            skf.setKeypair(kp);
            skf.setCrypt(null);
            skf.setSecretkey(sk);
            skf.setHead(head);
            try {
                SecretKeyFileLS.store(skf);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        @Override
        public boolean delete() {
            try {
                Files.delete(this.file);
                return true;
            } catch (IOException e) {
                // throw new RuntimeException(e);
                Logs.error("error:" + this, e);
                return false;
            }
        }

        @Override
        public boolean exists() {
            return Files.exists(this.file);
        }

        @Override
        public SecretKey fetch() {
            SecretKey sk = this.cached;
            if (sk != null) {
                return sk;
            }
            KeyPair kp = this.fetchKeyPair(false);
            SecretKeyFile skFile = new SecretKeyFile();
            skFile.setKeypair(kp);
            skFile.setAlias(this.alias);
            skFile.setFile(this.file);
            try {
                skFile = SecretKeyFileLS.load(skFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            sk = skFile.getSecretkey();
            this.cached = sk;
            return sk;
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
