package com.bitwormhole.passwordgm.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class HashUtils {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA512 = "SHA-512";


    private HashUtils() {
    }

    private static class Hash {

        String algorithm;
        MessageDigest digest;

        Hash(String alg) {
            this.algorithm = alg;
        }

        MessageDigest getMD() {
            MessageDigest md = this.digest;
            if (md != null) {
                return md;
            }
            try {
                md = MessageDigest.getInstance(this.algorithm);
                this.digest = md;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return md;
        }

        void append(String text) {
            if (text == null) {
                return;
            }
            byte[] bin = text.getBytes(StandardCharsets.UTF_8);
            this.append(bin);
        }

        void append(byte[] bin) {
            if (bin == null) {
                return;
            }
            MessageDigest md = this.getMD();
            md.update(bin);
        }

        byte[] sum() {
            MessageDigest md = this.getMD();
            return md.digest();
        }

        String sumHex() {
            byte[] s = this.sum();
            return Hex.stringify(s);
        }
    }

    public static byte[] sum(byte[] data, String algorithm) {
        Hash h = new Hash(algorithm);
        h.append(data);
        return h.sum();
    }

    public static String hexSum(byte[] data, String algorithm) {
        Hash h = new Hash(algorithm);
        h.append(data);
        return h.sumHex();
    }

    public static byte[] sum(String data, String algorithm) {
        Hash h = new Hash(algorithm);
        h.append(data);
        return h.sum();
    }

    public static String hexSum(String data, String algorithm) {
        Hash h = new Hash(algorithm);
        h.append(data);
        return h.sumHex();
    }
}
