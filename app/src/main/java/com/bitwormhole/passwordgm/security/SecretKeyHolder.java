package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;

import javax.crypto.SecretKey;


/***
 * [已废弃] 用 RepositorySecretKey 代替
 * */

@Deprecated

public interface SecretKeyHolder {

    KeyAlias alias();

    boolean create();

    boolean delete();

    boolean exists();

    SecretKey fetch();

}
