package com.bitwormhole.passwordgm.utils;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

public class PropertySetter {

    private final PropertyTable properties;

    public PropertySetter() {
        this.properties = PropertyTable.Factory.create();
    }

    public PropertySetter(PropertyTable _target) {
        this.properties = _target;
    }

    private void innerSet(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        this.properties.put(name, value);
    }

    public void put(String name, String value) {
        innerSet(name, value);
    }


    /**
     * set byte[] auto
     */
    public void put(String name, byte[] value) {
        putAuto(name, value);
    }

    /**
     * set byte[] auto
     */
    public void putAuto(String name, byte[] value) {
        if (name == null || value == null) {
            return;
        }
        if (value.length <= 64) {
            putHex(name, value);
            return;
        }
        putBase64(name, value);
    }

    /**
     * set byte[] as hex
     */
    public void putHex(String name, byte[] value) {
        String hex = Hex.stringify(value);
        innerSet(name, "hex:" + hex);
    }

    /**
     * set byte[] as base64
     */
    public void putBase64(String name, byte[] value) {
        String b64 = Base64.encode(value);
        innerSet(name, "base64:" + b64);
    }


    public void put(String name, int value) {
        innerSet(name, String.valueOf(value));
    }

    public void put(String name, boolean value) {
        innerSet(name, String.valueOf(value));
    }

    public void put(String name, PaddingMode value) {
        if (value == null) {
            return;
        }
        innerSet(name, value.name());
    }

    public void put(String name, CipherMode value) {
        if (value == null) {
            return;
        }
        innerSet(name, value.name());
    }
}
