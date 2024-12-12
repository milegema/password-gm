package com.bitwormhole.passwordgm.data.store;

import java.nio.file.Path;

public class TableFileLocation {


    // params


    private TableSelector selector;

    // results

    private Path folder;
    private Path file;
    private Path fileB; // Base
    private Path fileA; // Append


    public TableFileLocation() {
    }

    public TableSelector getSelector() {
        return selector;
    }

    public void setSelector(TableSelector selector) {
        this.selector = selector;
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
