package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TableRW {

    private final TableName name;
    private final TableReader reader;
    private final TableWriter writer;
    private final Set<String> fields;

    private TableRW(Builder b) {
        this.name = b.name;
        this.reader = b.reader;
        this.writer = b.writer;
        this.fields = b.fields;
    }

    public static class Builder {

        public TableName name;
        public TableReader reader;
        public TableWriter writer;
        public final Set<String> fields;

        public Builder() {
            this.fields = new HashSet<>();
        }

        public TableRW create() {
            return new TableRW(this);
        }
    }


    private String keyForField(String field, String id) {
        return String.valueOf(this.name) + '.' + id + '.' + field;
    }


    public String[] listAllIds() {
        List<String> ids = new ArrayList<>();
        try {
            PropertyTable pt = this.reader.read();
            String[] all = pt.names();
            final String prefix = this.name + ".";
            final String suffix = ".id";
            for (String key : all) {
                if (key.startsWith(prefix) && key.endsWith(suffix)) {
                    String id = key.substring(prefix.length(), key.length() - suffix.length());
                    ids.add(id);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ids.toArray(new String[0]);
    }


    public void put(String id, Map<String, String> values) {
        values.put("id", id);
        PropertyTable pt = PropertyTable.Factory.create();
        for (String field : this.fields) {
            String value = values.get(field);
            String key = this.keyForField(field, id);
            if (value == null) {
                continue;
            }
            pt.put(key, value);
        }
        try {
            this.writer.write(pt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> get(String id) {
        return this.get(id, null);
    }

    public Map<String, String> get(String id, Map<String, String> dst) {
        if (dst == null) {
            dst = new HashMap<>();
        }
        dst.put("this.table", this.name + "");
        dst.put("id", id);
        try {
            PropertyTable src = this.reader.read();
            for (String field : this.fields) {
                String key = this.keyForField(field, id);
                String value = src.get(key);
                dst.put(field, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dst;
    }

    public void flush() throws IOException {
        this.writer.flush();
    }
}
