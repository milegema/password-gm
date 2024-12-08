package com.bitwormhole.passwordgm.utils;

import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.util.Properties;

public class PropertySetter {

    private final Properties properties;

    public PropertySetter() {
        this.properties = new Properties();
    }

    public PropertySetter(Properties _target) {
        this.properties = _target;
    }

    private void innerSet(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        this.properties.setProperty(name, value);
    }

    public void put(String name, String value) {
        innerSet(name, value);
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
