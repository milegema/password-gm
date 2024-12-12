package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.UserAlias;

public class TableSelector {

    private ContextScope scope;
    private UserAlias user;
    private TableName table;

    public TableSelector() {
    }

    public TableSelector(TableSelector src) {
        if (src == null) {
            return;
        }
        this.scope = src.scope;
        this.user = src.user;
        this.table = src.table;
    }

    public ContextScope getScope() {
        return scope;
    }

    public void setScope(ContextScope scope) {
        this.scope = scope;
    }

    public UserAlias getUser() {
        return user;
    }

    public void setUser(UserAlias user) {
        this.user = user;
    }

    public TableName getTable() {
        return table;
    }

    public void setTable(TableName table) {
        this.table = table;
    }
}
