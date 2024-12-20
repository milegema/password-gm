package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.repositories.objects.ObjectManager;
import com.bitwormhole.passwordgm.data.repositories.refs.RefManager;
import com.bitwormhole.passwordgm.data.repositories.tables.TableManager;

import java.nio.file.Path;

public abstract class Repository {

    public static Repository getInstance(RepositoryParams params) {
        return RepositoryFactory.open(params);
    }

    public abstract Path location();

    public abstract RepositoryKey key();

    public abstract RepositoryConfig config();

    public abstract RefManager refs();

    public abstract TableManager tables();

    public abstract ObjectManager objects();

}
