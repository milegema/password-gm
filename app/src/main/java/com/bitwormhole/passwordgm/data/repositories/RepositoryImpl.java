package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.repositories.objects.ObjectManager;
import com.bitwormhole.passwordgm.data.repositories.refs.RefManager;
import com.bitwormhole.passwordgm.data.repositories.tables.RepoTableManager;

import java.nio.file.Path;

public class RepositoryImpl extends Repository {

    private final RepositoryContext context;

    public RepositoryImpl(RepositoryContext ctx) {
        this.context = ctx;
    }

    @Override
    public Path location() {
        return this.context.getLocation();
    }

    @Override
    public RepositoryKey key() {
        return this.context.getSecretKeyManager();
    }

    @Override
    public RepositoryConfig config() {
        return this.context.getConfig();
    }

    @Override
    public RefManager refs() {
        return this.context.getRefManager();
    }

    @Override
    public RepoTableManager tables() {
        return this.context.getTableManager();
    }

    @Override
    public ObjectManager objects() {
        return this.context.getObjectManager();
    }
}
