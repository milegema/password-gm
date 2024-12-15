package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.ids.RepositoryAlias;
import com.bitwormhole.passwordgm.security.KeyFingerprint;

public interface RepositoryHolder {

    RepositoryAlias alias();

    Repository fetch();

    Repository create();

    boolean exists();

    boolean delete();

}
