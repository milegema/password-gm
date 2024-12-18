package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectManager;
import com.bitwormhole.passwordgm.data.repositories.refs.RefManager;

import java.nio.file.Path;

public abstract class Repository {

    public static Repository getInstance(RepositoryParams params) {
        return RepositoryFactory.open(params);
    }

    public abstract Path location();

    public abstract RepositorySecretKey key();

    public abstract RepositoryConfig config();

    public abstract RefManager refs();

    public abstract ObjectManager objects();

}
