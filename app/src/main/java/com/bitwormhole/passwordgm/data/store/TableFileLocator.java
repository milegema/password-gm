package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.UserAlias;

import java.nio.file.Path;

public final class TableFileLocator {

    private TableFileLocator() {
    }

    public static TableFileLocation locate(TableContext ctx, TableSelector sel) {

        ContextScope scope = sel.getScope();
        UserAlias user = sel.getUser();
        TableName table_name = sel.getTable();
        StringBuilder builder = new StringBuilder();
        TableFileLocation location = new TableFileLocation();

        switch (scope) {
            case ROOT:
            case APP:
                builder.append("var/lib/");
                break;
            case USER:
            default:
                builder.append("home/").append(user).append('/');
                break;
        }
        builder.append(table_name);
        Path dir = ctx.getBaseDir().resolve(builder.toString());

        location.setSelector(sel);
        location.setFile(dir.resolve("table"));
        location.setFileA(dir.resolve("table.a"));
        location.setFileB(dir.resolve("table.b"));
        location.setFolder(dir);
        return location;
    }
}
