package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;

import javax.crypto.SecretKey;

public interface SecretKeyHolder {

    KeyAlias alias();

    boolean create();

    boolean delete();

    boolean exists();

    SecretKey fetch();

}
