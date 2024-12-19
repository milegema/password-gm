package com.bitwormhole.passwordgm.data.repositories.refs;

import java.nio.file.Path;

import javax.crypto.SecretKey;

public class RefContext {

    private Path path;
    private RefFile file;
    private RefFolder folder;
    private RefHolder holder;
    private RefName name;
    private SecretKey key;

    public RefContext() {
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public RefName getName() {
        return name;
    }

    public void setName(RefName name) {
        this.name = name;
    }

    public RefHolder getHolder() {
        return holder;
    }

    public void setHolder(RefHolder holder) {
        this.holder = holder;
    }

    public RefFile getFile() {
        return file;
    }

    public void setFile(RefFile file) {
        this.file = file;
    }

    public RefFolder getFolder() {
        return folder;
    }

    public void setFolder(RefFolder folder) {
        this.folder = folder;
    }
}
