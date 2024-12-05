package com.bitwormhole.passwordgm.security.pem;

public class PEMBlock {

    private String type;
    private byte[] data;

    public PEMBlock() {
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
