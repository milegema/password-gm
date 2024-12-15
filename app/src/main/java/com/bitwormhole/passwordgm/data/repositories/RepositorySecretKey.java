package com.bitwormhole.passwordgm.data.repositories;

import javax.crypto.SecretKey;

public interface RepositorySecretKey {

    boolean create();

    boolean delete();

    boolean exists();

    SecretKey fetch();

}
