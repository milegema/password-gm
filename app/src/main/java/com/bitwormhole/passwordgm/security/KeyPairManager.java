package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;

public interface KeyPairManager {

    KeyPairHolder get(KeyAlias alias);

    KeyAlias[] listAliases();

}
