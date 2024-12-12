package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;

import java.security.KeyPair;

public interface KeyPairHolder {

    boolean create();

    boolean delete();

    boolean exists();

    KeyPair fetch();

    KeyAlias alias();

}
