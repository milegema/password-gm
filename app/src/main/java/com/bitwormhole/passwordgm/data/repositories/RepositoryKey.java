package com.bitwormhole.passwordgm.data.repositories;

import javax.crypto.SecretKey;


/**
 * the SecretKey Manager for Repository
 * */
public interface RepositoryKey {

    boolean create();

    boolean delete();

    boolean exists();

    SecretKey fetch();

}
