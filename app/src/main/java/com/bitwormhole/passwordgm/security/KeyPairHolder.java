package com.bitwormhole.passwordgm.security;

import java.security.KeyPair;

public interface KeyPairHolder {

    boolean create();

    boolean delete();

    boolean exists();

    KeyPair get();

}
