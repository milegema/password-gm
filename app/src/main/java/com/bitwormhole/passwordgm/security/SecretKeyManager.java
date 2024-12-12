package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;

public interface SecretKeyManager {

    SecretKeyHolder get(KeyAlias alias);
}
