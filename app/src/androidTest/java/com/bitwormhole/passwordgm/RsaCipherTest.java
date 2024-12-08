package com.bitwormhole.passwordgm;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.CipherUtils;
import com.bitwormhole.passwordgm.security.Encryption;
import com.bitwormhole.passwordgm.security.PaddingMode;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;


@RunWith(AndroidJUnit4.class)
public class RsaCipherTest {

    @Test
    public void useCipher() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException, CertificateException, IOException, UnrecoverableEntryException {

        String alias = this.getClass().getName();
        byte[] data1 = "hello, RsaCipherTest".getBytes();
        String algorithm = "RSA";
        final String provider = "AndroidKeyStore";
        final String provider2 = "AndroidOpenSSL";

        // key-gen
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, provider);
        kpg.initialize(new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setKeySize(1024 * 2)
                .build());
        KeyPair pair = kpg.generateKeyPair();

        // key-store
        KeyStore store = KeyStore.getInstance(provider);
        store.load(null);
        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) store.getEntry(alias, null);


        // encrypt

        Encryption en1 = new Encryption();
        en1.setPlain(data1);
        en1.setAlgorithm(algorithm);
        en1.setMode(CipherMode.NONE);
        en1.setPadding(PaddingMode.PKCS1Padding);
        // en1.setProvider(provider2);


        Encryption en2 = CipherUtils.encrypt(en1, pair.getPublic());
        en2.setProvider(null);
        Encryption en3 = CipherUtils.decrypt(en2, entry.getPrivateKey());


        byte[] data9 = en3.getPlain();
        Logs.info(en3.toString());
        Assert.assertArrayEquals(data1, data9);
    }
}
