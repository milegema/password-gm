package com.bitwormhole.passwordgm.security;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.bitwormhole.passwordgm.utils.Errors;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Date;

public class KeyPairManagerImpl implements KeyPairManager {


    @Override
    public KeyPairHolder get(KeySelector sel) {
        String alias = getAliasOf(sel);
        return new MyKeyHolder(alias);
    }


    private static class MyKeyHolder implements KeyPairHolder {

        private final String mAlias;
        private KeyPair mKeyPair;

        public MyKeyHolder(String name) {
            this.mAlias = name;
        }

        @Override
        public String alias() {
            return this.mAlias;
        }

        @Override
        public boolean create() {
            long now = System.currentTimeMillis();
            Date t1 = new Date(now - 1000);
            Date t2 = new Date(now + (1000L * 99L * 365 * 24 * 3600));
            int purposes = KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
            int keySize = 1024 * 4;
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
                kpg.initialize(new KeyGenParameterSpec.Builder(mAlias, purposes)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                        .setCertificateNotBefore(t1).setCertificateNotAfter(t2)
                        .setBlockModes(KeyProperties.BLOCK_MODE_ECB, KeyProperties.BLOCK_MODE_CBC, KeyProperties.BLOCK_MODE_CTR)
                        .setKeySize(keySize)
                        .build());
                KeyPair kp = kpg.generateKeyPair();
                if (kp == null) {
                    throw new RuntimeException("result of generateKeyPair() is null");
                }
                return true;
            } catch (NoSuchProviderException | NoSuchAlgorithmException |
                     InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean delete() {
            try {
                KeyStore store = getKeyStore();
                store.deleteEntry(mAlias);
                return true;
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     IOException e) {
                // throw new RuntimeException(e);
                Errors.handle(null, e);
                return false;
            }
        }

        @Override
        public boolean exists() {
            try {
                KeyStore store = getKeyStore();
                return store.containsAlias(mAlias);
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     IOException e) {
                //  throw new RuntimeException(e);
                Errors.handle(null, e);
                return false;
            }
        }

        @Override
        public KeyPair fetch() {
            KeyPair kp = this.mKeyPair;
            if (kp != null) {
                return kp;
            }
            try {
                KeyStore store = getKeyStore();
                KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) store.getEntry(mAlias, null);
                PrivateKey pri = entry.getPrivateKey();
                PublicKey pub = entry.getCertificate().getPublicKey();
                kp = new KeyPair(pub, pri);
                this.mKeyPair = kp;
                return kp;
            } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException |
                     UnrecoverableEntryException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static KeyStore getKeyStore() throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore store = KeyStore.getInstance("AndroidKeyStore");
        store.load(null);
        return store;
    }

    private static String getAliasOf(KeySelector sel) {
        return KeySelector.computeAlias(sel);
    }
}
