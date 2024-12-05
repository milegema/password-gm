package com.bitwormhole.passwordgm.security;

public interface SecretKeyManager {

    SecretKeyHolder get(KeySelector sel);
}
