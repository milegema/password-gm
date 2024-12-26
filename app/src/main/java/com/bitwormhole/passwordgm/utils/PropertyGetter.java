package com.bitwormhole.passwordgm.utils;

import android.util.NoSuchPropertyException;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

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

    public long getLong(String name, long def) {
        String str = innerGet(name);
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public short getShort(String name, short def) {
        String str = innerGet(name);
        try {
            return Short.parseShort(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public byte[] getDataHex(String name, byte[] def) {
        String str = innerGet(name);
        try {
            final String prefix = "hex:";
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length()).trim();
            }
            return Hex.parse(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public byte[] getDataBase64(String name, byte[] def) {
        String str = innerGet(name);
        try {
            final String prefix = "base64:";
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length()).trim();
            }
            return Base64.decode(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public byte[] getDataAuto(String name, byte[] def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        } else if (str.startsWith("base64:")) {
            return this.getDataBase64(name, def);
        } else if (str.startsWith("hex:")) {
            return this.getDataHex(name, def);
        }
        if (Hex.isHexString(str)) {
            return Hex.parse(str);
        }
        return Base64.decode(str);
    }


    public byte[] getData(String name, byte[] def) {
        return getDataAuto(name, def);
    }


    public BlockType getBlockType(String name, BlockType def) {
        String str = innerGet(name);
        try {
            return BlockType.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public BlockID getBlockID(String name, BlockID def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        if (str.isEmpty()) {
            return def;
        }
        try {
            return new BlockID(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public RefName getRefName(String name, RefName def) {
        String str = innerGet(name);
        if (str == null) {
            return def;
        }
        if (str.isEmpty()) {
            return def;
        }
        try {
            return new RefName(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }


    public PaddingMode getPaddingMode(String name, PaddingMode def) {
        String str = innerGet(name);
        try {
            return PaddingMode.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }

    public CipherMode getCipherMode(String name, CipherMode def) {
        String str = innerGet(name);
        try {
            return CipherMode.valueOf(str);
        } catch (Exception e) {
            Errors.handle(null, e);
        }
        return def;
    }
}
