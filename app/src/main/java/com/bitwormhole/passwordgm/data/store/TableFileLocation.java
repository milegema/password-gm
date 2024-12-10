package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.UserAlias;

import java.nio.file.Path;

public class TableFileLocation {


    // params

    private ContextScope scope;
    private UserAlias user;
    private TableName table;

    // results

    private Path folder;
    private Path file;
    private Path fileB; // Base
    private Path fileA; // Append


    public TableFileLocation() {
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

    public Path getFolder() {
        return folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public Path getFileB() {
        return fileB;
    }

    public void setFileB(Path fileB) {
        this.fileB = fileB;
    }

    public Path getFileA() {
        return fileA;
    }

    public void setFileA(Path fileA) {
        this.fileA = fileA;
    }
}
