package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.contexts.ContextScope;

import java.nio.file.Path;

public abstract class Repository {

    public static Repository getInstance(RepositoryParams params) {
        return RepositoryFactory.open(params);
    }

    public abstract Path location();

    public abstract ContextScope scope();

}
