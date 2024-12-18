package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.nio.file.Path;

public interface RepositoryConfig {

    Path file();

    PropertyTable loadProperties();

    void store(PropertyTable pt);

}
