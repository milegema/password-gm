package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectManager;
import com.bitwormhole.passwordgm.data.repositories.refs.RefManager;

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
    public RepositorySecretKey key() {
        return this.context.getSecretKey2repo();
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
    public ObjectManager objects() {
        return this.context.getObjectManager();
    }
}
