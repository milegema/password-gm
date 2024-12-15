package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;


/***
 * [已废弃] 用 RepositorySecretKey 代替
 * */

@Deprecated


public interface SecretKeyManager {

    SecretKeyHolder get(KeyAlias alias);
}
