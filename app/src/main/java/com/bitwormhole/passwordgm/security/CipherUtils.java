package com.bitwormhole.passwordgm.security;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public final class CipherUtils {

    private CipherUtils() {
    }

    public static Encryption encrypt(Encryption src, PublicKey pk) {
        try {
            String provider = src.getProvider();
            Encryption dst = new Encryption(src);
            String transformation = getTransformationOf(src);
            Cipher cipher = getCipher(transformation, provider);
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            cipher.update(src.getPlain());
            dst.setEncrypted(cipher.doFinal());
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Encryption encrypt(Encryption src, SecretKey sk) {
        try {
            String provider = src.getProvider();
            String transformation = getTransformationOf(src);
            Encryption dst = new Encryption(src);
            IvParameterSpec iv = getIvOf(src);

            Cipher cipher = getCipher(transformation, provider);
            cipher.init(Cipher.ENCRYPT_MODE, sk, iv);
            cipher.update(src.getPlain());

            dst.setEncrypted(cipher.doFinal());
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Encryption decrypt(Encryption src, PrivateKey pk) {
        try {
            String provider = src.getProvider();
            Encryption dst = new Encryption(src);
            String transformation = getTransformationOf(src);
            Cipher cipher = getCipher(transformation, provider);
            cipher.init(Cipher.DECRYPT_MODE, pk);
            cipher.update(src.getEncrypted());
            dst.setPlain(cipher.doFinal());
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Encryption decrypt(Encryption src, SecretKey sk) {
        try {
            String provider = src.getProvider();
            String transformation = getTransformationOf(src);
            Encryption dst = new Encryption(src);
            IvParameterSpec iv = getIvOf(src);

            Cipher cipher = getCipher(transformation, provider);
            cipher.init(Cipher.DECRYPT_MODE, sk, iv);
            cipher.update(src.getEncrypted());

            dst.setPlain(cipher.doFinal());
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher(String transformation, String provider) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        if (provider == null) {
            return Cipher.getInstance(transformation);
        } else {
            return Cipher.getInstance(transformation, provider);
        }
    }

    private static IvParameterSpec getIvOf(Encryption src) {
        if (src == null) {
            return null;
        }
        byte[] iv = src.getIv();
        if (iv == null) {
            return null;
        }
        return new IvParameterSpec(iv);
    }

    private static String getTransformationOf(Encryption src) {

        String alg = src.getAlgorithm();
        CipherMode mode = src.getMode();
        PaddingMode padding = src.getPadding();

        if (alg == null) {
            alg = "AES";
        }
        if (mode == null) {
            mode = CipherMode.CBC;
        }
        if (padding == null) {
            padding = PaddingMode.PKCS7Padding;
        }

        String x = alg.toUpperCase();
        String y = mode.name();
        String z = padding.name();
        return x + '/' + y + '/' + z;
    }
}
