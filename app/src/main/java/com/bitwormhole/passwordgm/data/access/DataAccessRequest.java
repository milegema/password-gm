package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public class DataAccessRequest {

    private DataAccessStack stack;

    private Path file;
    private byte[] raw; // 直接从文件读写的原始数据
    private DataAccessMode dam;

    private PEMDocument pem;


    private KeyPair keyPair;
    private SecretKey secretKey;
    private byte[] iv;
    private PaddingMode padding;
    private CipherMode mode;


    private DataAccessBlock[] blocks;


    public DataAccessRequest() {
    }

    public DataAccessStack getStack() {
        return stack;
    }

    public void setStack(DataAccessStack stack) {
        this.stack = stack;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public byte[] getRaw() {
        return raw;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }

    public PEMDocument getPem() {
        return pem;
    }

    public void setPem(PEMDocument pem) {
        this.pem = pem;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public PaddingMode getPadding() {
        return padding;
    }

    public void setPadding(PaddingMode padding) {
        this.padding = padding;
    }

    public CipherMode getMode() {
        return mode;
    }

    public void setMode(CipherMode mode) {
        this.mode = mode;
    }

    public DataAccessBlock[] getBlocks() {
        return blocks;
    }

    public void setBlocks(DataAccessBlock[] blocks) {
        this.blocks = blocks;
    }

    public DataAccessMode getDam() {
        return dam;
    }

    public void setDam(DataAccessMode dam) {
        this.dam = dam;
    }
}
