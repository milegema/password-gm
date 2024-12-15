package com.bitwormhole.passwordgm.data.ids;

import com.bitwormhole.passwordgm.security.KeyFingerprint;

public class RepositoryAlias extends Alias {

    public RepositoryAlias(String v) {
        super(v);
    }


    public RepositoryAlias(KeyFingerprint fp) {
        super(fp.toString());
    }

}
