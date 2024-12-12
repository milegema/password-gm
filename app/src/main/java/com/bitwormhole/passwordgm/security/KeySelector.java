package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.data.ids.UserAlias;
import com.bitwormhole.passwordgm.utils.HashUtils;

public final class KeySelector {

    private KeySelector() {
    }

    public static KeyAlias alias(UserAlias user) {
        String name = null;
        if (user != null) {
            name = user.getValue();
        }
        return alias(ContextScope.USER, name);
    }

    public static KeyAlias alias(ContextScope scope, String name) {
        StringBuilder b = new StringBuilder();
        b.append(scope).append(":").append(name);
        String sum = HashUtils.hexSum(b.toString(), HashUtils.SHA1);
        String id = sum.substring(0, 15);
        b.setLength(0);
        b.append(scope).append("_").append(id);
        return new KeyAlias(b.toString());
    }
}
