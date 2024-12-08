package com.bitwormhole.passwordgm.security;

public class Encryption {

    private String provider;

    private String algorithm; // [AES|RSA|...]
    private CipherMode mode; // [ECB|CBC|CTR|CFB|OFB|...]
    private PaddingMode padding; // [PKCS7|PKCS5|Zero|No|...]

    private byte[] iv;
    private byte[] encrypted;
    private byte[] plain;

    public Encryption() {
    }

    public Encryption(Encryption src) {
        if (src != null) {
            this.algorithm = src.algorithm;
            this.mode = src.mode;
            this.padding = src.padding;
            this.iv = src.iv;
            this.encrypted = src.encrypted;
            this.plain = src.plain;
            this.provider = src.provider;
        }
    }


    public byte[] getPlain() {
        return plain;
    }

    public void setPlain(byte[] plain) {
        this.plain = plain;
    }

    public byte[] getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(byte[] encrypted) {
        this.encrypted = encrypted;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }


    public CipherMode getMode() {
        return mode;
    }

    public void setMode(CipherMode mode) {
        this.mode = mode;
    }

    public PaddingMode getPadding() {
        return padding;
    }

    public void setPadding(PaddingMode padding) {
        this.padding = padding;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
