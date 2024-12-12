package com.bitwormhole.passwordgm.data.ids;

import androidx.annotation.NonNull;

public class Alias {

    private final static String undefined = "undefined";

    private final String value;

    public Alias(String v) {
        this.value = normalizeValue(v);
    }

    public Alias(Alias al) {
        this.value = normalizeValue(al);
    }

    private static String normalizeValue(String v) {
        if (v == null) {
            return undefined;
        }
        if (v.isEmpty()) {
            return undefined;
        }
        return v.toLowerCase();
    }

    private static String normalizeValue(Alias alias) {
        if (alias == null) {
            return undefined;
        }
        return normalizeValue(alias.value);
    }

    public String getValue() {
        return this.value;
    }


    @NonNull
    @Override
    public String toString() {
        return this.value;
    }
}
