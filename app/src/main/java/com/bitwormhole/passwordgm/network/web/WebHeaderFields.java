package com.bitwormhole.passwordgm.network.web;

import java.util.HashMap;
import java.util.Map;

public class WebHeaderFields {

    private final Map<String, String> fields;

    public WebHeaderFields() {
        this.fields = new HashMap<>();
    }

    public WebHeaderFields(WebHeaderFields src) {
        if (src == null) {
            this.fields = new HashMap<>();
        } else {
            this.fields = new HashMap<>(src.fields);
        }
    }

    public void set(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        fields.put(name, value);
    }

    public String get(String name) {
        return fields.get(name);
    }

}
