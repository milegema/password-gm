package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.security.KeyPairHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeySelector;

import java.security.KeyPair;

public class AppKeyLoader {

    private final AppContext app;

    public AppKeyLoader(ContextHolder ch) {
        this.app = ch.getApp();
    }


    public void load() {

        KeyAlias alias = KeySelector.alias(ContextScope.APP, "root");
        KeyPairManager kpm = app.getKeyPairManager();
        KeyPairHolder kph = kpm.get(alias);

        if (!kph.exists()) {
            kph.create();
        }

        KeyPair pair = kph.fetch();
        app.setKeyPair(pair);
        app.setSecretKey(null);
    }
}
