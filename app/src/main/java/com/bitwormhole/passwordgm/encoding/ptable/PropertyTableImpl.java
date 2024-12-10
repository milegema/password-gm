package com.bitwormhole.passwordgm.encoding.ptable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class PropertyTableImpl implements PropertyTable {

    private final Map<String, String> table;

    PropertyTableImpl() {
        Map<String, String> t = new HashMap<>();
        this.table = Collections.synchronizedMap(t);
    }

    @Override
    public void put(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        table.put(name, value);
    }

    @Override
    public String get(String name) {
        return table.get(name);
    }

    @Override
    public void remove(String name) {
        table.remove(name);
    }

    @Override
    public String[] names() {
        Set<String> src = table.keySet();
        String[] dst = new String[0];
        dst = src.toArray(dst);
        Arrays.sort(dst);
        return dst;
    }
}
