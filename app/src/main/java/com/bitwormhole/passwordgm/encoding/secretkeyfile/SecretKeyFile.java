package com.bitwormhole.passwordgm.encoding.secretkeyfile;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public class SecretKeyFile {

    private Path file; // 文件的路径
    private SecretKey inner; // 内部密钥, 是被保存在文件内的实体
    private KeyPair outer; // 外部密钥对, 用于对 inner 进行加密

    public SecretKeyFile() {
    }

    public SecretKeyFile(SecretKeyFile src) {
        if (src != null) {
            this.file = src.file;
            this.inner = src.inner;
            this.outer = src.outer;
        }
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public KeyPair getOuter() {
        return outer;
    }

    public void setOuter(KeyPair outer) {
        this.outer = outer;
    }

    public SecretKey getInner() {
        return inner;
    }

    public void setInner(SecretKey inner) {
        this.inner = inner;
    }
}
