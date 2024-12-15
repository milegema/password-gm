package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.RepositoryAlias;

import java.nio.file.Path;
import java.security.KeyPair;

public class RepositoryParams {

    private RepositoryAlias alias;
    private Path location;
    private KeyPair keyPair;
    private ContextScope scope;


    public RepositoryParams() {
    }

    public RepositoryParams(RepositoryParams src) {
        if (src == null) {
            return;
        }
        this.alias = src.alias;
        this.keyPair = src.keyPair;
        this.location = src.location;
        this.scope = src.scope;
    }


    public RepositoryAlias getAlias() {
        return alias;
    }

    public void setAlias(RepositoryAlias alias) {
        this.alias = alias;
    }

    public ContextScope getScope() {
        return scope;
    }

    public void setScope(ContextScope scope) {
        this.scope = scope;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }
}
