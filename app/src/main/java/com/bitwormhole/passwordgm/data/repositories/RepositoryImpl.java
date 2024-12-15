package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.contexts.ContextScope;

import java.nio.file.Path;

public class RepositoryImpl extends Repository {

    private final RepositoryContext context;

    public RepositoryImpl(RepositoryContext ctx) {
        this.context = ctx;
    }

    @Override
    public Path location() {
        return this.context.getLayout().getRepository();
    }

    @Override
    public ContextScope scope() {
        return this.context.getScope();
    }

    @Override
    public RepositorySecretKey key() {
        return null; //   this.context. ;
    }
}
