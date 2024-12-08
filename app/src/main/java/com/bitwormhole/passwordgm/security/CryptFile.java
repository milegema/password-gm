package com.bitwormhole.passwordgm.security;

import java.util.Properties;

public class CryptFile {

    private Properties head; // plain meta
    private byte[] body; // encrypted data

    public CryptFile() {
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Properties getHead() {
        return head;
    }

    public void setHead(Properties head) {
        this.head = head;
    }
}
