package com.bitwormhole.passwordgm.data.ids;

public class Alias {

    private final static String undefined = "undefined";

    private final String value;

    public Alias(String v) {
        if (v == null) {
            v = undefined;
        }
        if (v.isEmpty()) {
            v = undefined;
        }
        this.value = v;
    }

    public String getValue() {
        return value;
    }
}
