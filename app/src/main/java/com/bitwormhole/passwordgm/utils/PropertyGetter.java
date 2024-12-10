package com.bitwormhole.passwordgm.utils;

import android.util.NoSuchPropertyException;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.util.Properties;

public class PropertyGetter {

    private PropertyTable properties;
    private boolean required;

    public PropertyGetter() {
        this.properties = PropertyTable.Factory.create();
        this.required = true;
    }

    public PropertyGetter(PropertyTable src) {
        if (src == null) {
            src = PropertyTable.Factory.create();
        }
        this.properties = src;
        this.required = true;
    }


    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable p) {
        if (p == null) {
            return;
        }
        this.properties = p;
    }

    private String innerGet(String name) {
        String str = this.properties.get(name);
        if (str == null) {
            if (this.required) {
                throw new NoSuchPropertyException("no property named: " + name);
            }
        }
        return str;
    }


    public String getString(String name, String def) {
        return innerGet(name);
    }

    public int getInt(String name, int def) {
        String str = innerGet(name);
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public PaddingMode getPaddingMode(String name, PaddingMode def) {
        String str = innerGet(name);
        PaddingMode value = null;
        try {
            value = PaddingMode.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        if (value == null) {
            value = def;
        }
        return value;
    }

    public CipherMode getCipherMode(String name, CipherMode def) {
        String str = innerGet(name);
        CipherMode value = null;
        try {
            value = CipherMode.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        if (value == null) {
            value = def;
        }
        return value;
    }

}
