package com.bitwormhole.passwordgm.data.repositories.tables;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class TableName {

    private final static String UNNAMED = "unnamed";


    private final String mName;

    private TableName() {
        this.mName = UNNAMED;
    }

    public TableName(String name) {
        this.mName = normalize(name);
    }

    public TableName(TableName name) {
        if (name == null) {
            this.mName = UNNAMED;
            return;
        }
        this.mName = normalize(name.mName);
    }

    private static String normalize(String name) {
        if (name == null) {
            return UNNAMED;
        }
        name = name.trim().toLowerCase();

        if (name.isEmpty()) {
            return UNNAMED;
        }
        return name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof TableName) {
            TableName tn = (TableName) obj;
            return this.mName.equals(tn.mName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.mName.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this.mName;
    }
}
