package com.bitwormhole.passwordgm.encoding.cryptfile;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

public class CryptBlock {

    private PropertyTable head; // plain meta
    private byte[] body; // encrypted body data

    public CryptBlock() {
    }

    public byte[] getBody() {
        return body;
    }

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
