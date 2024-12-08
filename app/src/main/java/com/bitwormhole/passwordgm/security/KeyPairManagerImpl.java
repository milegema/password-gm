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
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
                kpg.initialize(new KeyGenParameterSpec.Builder(
                        mAlias,
                        KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setDigests(KeyProperties.DIGEST_SHA256,
                                KeyProperties.DIGEST_SHA512)
                        .build());
                this.mKeyPair = kpg.generateKeyPair();
                return true;
            } catch (NoSuchProviderException | NoSuchAlgorithmException |
                     InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean delete() {
            try {
                KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
                ks.load(null);
                ks.deleteEntry(mAlias);
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
                KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
                ks.load(null);
                return ks.containsAlias(mAlias);
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
                KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
                ks.load(null);
                KeyStore.Entry entry = ks.getEntry(mAlias, null);
                KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) entry;
                PrivateKey pri = pkEntry.getPrivateKey();
                PublicKey pub = pkEntry.getCertificate().getPublicKey();
                kp = new KeyPair(pub, pri);
                this.mKeyPair = kp;
                return kp;
            } catch (CertificateException | KeyStoreException |
                     NoSuchAlgorithmException | UnrecoverableEntryException |
                     IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getAliasOf(KeySelector sel) {
        return KeySelector.computeAlias(sel);
    }
}
