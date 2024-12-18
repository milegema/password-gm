package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.nio.file.Path;

public class RepositoryConfigFile implements RepositoryConfig {

    public RepositoryConfigFile(RepositoryContext ctx) {
    }

    @Override
    public Path file() {
        return null;
    }

    @Override
    public PropertyTable loadProperties() {
        return null;
    }

    @Override
    public void store(PropertyTable pt) {

    }
}
