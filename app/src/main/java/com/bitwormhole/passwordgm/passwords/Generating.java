package com.bitwormhole.passwordgm.passwords;

public class Generating {

    private String app; // app_name
    private String user; // local_user_name
    private String domain; // 域名
    private String username; // username@domain
    private byte[] code;
    private char[] charset; // 构成密码的字符集合
    private int length; // 最终密码的长度
    private int revision; // 密码的版本

    private long createdAt;
    private long updatedAt;

    public Generating() {
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public char[] getCharset() {
        return charset;
    }

    public void setCharset(char[] charset) {
        this.charset = charset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
