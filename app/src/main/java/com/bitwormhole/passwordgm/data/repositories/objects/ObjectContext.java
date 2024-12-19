package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.repositories.RepositoryContext;

import java.io.IOException;
import java.nio.file.Path;

final class ObjectContext {

    private RepositoryContext parent;
    private ObjectID id;
    private ObjectHolder holder;
    private ObjectReader reader;
    private Path file ;

    ObjectContext() {
    }


    public RepositoryContext getParent() {
        return parent;
    }

    public void setParent(RepositoryContext parent) {
        this.parent = parent;
    }

    public ObjectID getId() {
        return id;
    }

    public void setId(ObjectID id) {
        this.id = id;
    }

    public ObjectHolder getHolder() {
        return holder;
    }

    public void setHolder(ObjectHolder holder) {
        this.holder = holder;
    }

    public ObjectReader getReader() {
        return reader;
    }

    public void setReader(ObjectReader reader) {
        this.reader = reader;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }
}
