package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.utils.Logs;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

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

            byte[] plain = src.getPlain();
            byte[] secret = cipher.doFinal(plain);

            dst.setEncrypted(secret);
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
            initCipher(cipher, Cipher.ENCRYPT_MODE, sk, iv);

            byte[] plain = src.getPlain();
            byte[] secret = cipher.doFinal(plain);

            dst.setEncrypted(secret);
            tmpDebugLogIV(iv);
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

            byte[] secret = src.getEncrypted();
            byte[] plain = cipher.doFinal(secret);

            dst.setPlain(plain);
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
            initCipher(cipher, Cipher.DECRYPT_MODE, sk, iv);

            byte[] secret = src.getEncrypted();
            byte[] plain = cipher.doFinal(secret);

            dst.setPlain(plain);
            tmpDebugLogIV(iv);
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // private methods ///////////////////


    private static void initCipher(Cipher cipher, int op_mode, SecretKey key, IvParameterSpec iv) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (iv == null) {
            cipher.init(op_mode, key);
            return;
        }
        cipher.init(op_mode, key, iv);
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
        if (iv.length == 0) {
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


    private static void tmpDebugLogIV(IvParameterSpec iv) {
        if (iv == null) {
            return;
        }
        byte[] bin = iv.getIV();
        String str = Arrays.toString(bin);
        Logs.debug("IV = " + str);
    }
}
