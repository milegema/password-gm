package com.bitwormhole.passwordgm.data.repositories;

import android.content.Context;

import java.security.KeyPair;

public abstract class RepositoryManager {

    private static RepositoryManager _inst;

    public static RepositoryManager getInstance(Context ctx) {
        RepositoryManager rm = _inst;
        if (rm == null) {
            rm = new RepositoryManagerImpl(ctx);
            _inst = rm;
        }
        return rm;
    }

    public abstract RepositoryHolder get(KeyPair kp);

    public abstract RepositoryHolder getRoot();

}
