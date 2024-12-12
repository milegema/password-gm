package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.encoding.cryptfile.CryptBlock;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

public class DataAccessBlock extends CryptBlock {

    private byte[] plain; // required
    private String text; // optional
    private PropertyTable properties; // optional

    public DataAccessBlock() {
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPlain() {
        return plain;
    }

    public void setPlain(byte[] plain) {
        this.plain = plain;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }
}
