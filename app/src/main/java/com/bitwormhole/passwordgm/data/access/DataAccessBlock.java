package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.encoding.blocks.CryptoBlock;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

public class DataAccessBlock {

    private CryptoBlock crypto;
    private PlainBlock plain;

    private String text; // optional : 用于文本格式
    private PropertyTable properties; // optional : 用于属性表格式

    public DataAccessBlock() {
    }


    public PlainBlock getPlain() {
        return plain;
    }

    public void setPlain(PlainBlock plain) {
        this.plain = plain;
    }

    public CryptoBlock getCrypto() {
        return crypto;
    }

    public void setCrypto(CryptoBlock crypto) {
        this.crypto = crypto;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }
}
