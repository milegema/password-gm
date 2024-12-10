package com.bitwormhole.passwordgm.encoding.cryptfile;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.util.Properties;

public class CryptFile {

    private PropertyTable head; // plain meta
    private byte[] body; // encrypted data

    public CryptFile() {
    }

    /**
     * get : encrypted data
     * */
    public byte[] getBody() {
        return body;
    }

    /**
     * set : encrypted data
     * */
    public void setBody(byte[] body) {
        this.body = body;
    }

    public PropertyTable getHead() {
        return head;
    }

    public void setHead(PropertyTable head) {
        this.head = head;
    }
}
