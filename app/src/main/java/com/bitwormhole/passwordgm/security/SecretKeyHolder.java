package com.bitwormhole.passwordgm.security;

import javax.crypto.SecretKey;

public interface SecretKeyHolder {

    String alias();

    boolean create();

    boolean delete();

    boolean exists();

    SecretKey fetch();

}
