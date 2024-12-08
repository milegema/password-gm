package com.bitwormhole.passwordgm;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.CipherUtils;
import com.bitwormhole.passwordgm.security.Encryption;
import com.bitwormhole.passwordgm.security.PaddingMode;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import javax.crypto.spec.SecretKeySpec;


@RunWith(AndroidJUnit4.class)
public class AesCipherTest {

    @Test
    public void useCipher() {


        byte[] data1 = "hello, aes".getBytes();
        byte[] key = new byte[256 / 8];
        byte[] iv = new byte[16];
        Random rand = new Random();
        rand.nextBytes(key);
        rand.nextBytes(iv);

        final String algorithm = "AES";
        SecretKeySpec sks = new SecretKeySpec(key, algorithm);


        Encryption en1 = new Encryption();
        en1.setAlgorithm(algorithm);
        en1.setMode(CipherMode.CBC);
        en1.setPadding(PaddingMode.PKCS7Padding);
        en1.setPlain(data1);
        en1.setIv(iv);


        Encryption en2 = CipherUtils.encrypt(en1, sks);
        Encryption en3 = CipherUtils.decrypt(en2, sks);

        byte[] data9 = en3.getPlain();
        Logs.info(en3.toString());
        Assert.assertArrayEquals(data1, data9);
    }
}
