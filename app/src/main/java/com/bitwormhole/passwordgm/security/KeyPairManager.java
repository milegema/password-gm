package com.bitwormhole.passwordgm.security;

public interface KeyPairManager {

    KeyPairHolder get(KeySelector sel);

}
