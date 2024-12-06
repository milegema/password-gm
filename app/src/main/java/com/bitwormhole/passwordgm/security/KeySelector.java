package com.bitwormhole.passwordgm.security;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.utils.HashUtils;

public class KeySelector {

    public ContextScope scope;
    public String name;

    public KeySelector() {
    }


    public static String computeAliasOf(KeySelector sel) {
        if (sel == null) {
            return "null";
        }
        StringBuilder b = new StringBuilder();
        b.append(sel.scope).append(":").append(sel.name);
        String sum = HashUtils.hexSum(b.toString(), HashUtils.SHA1);
        String id = sum.substring(0, 11);

        b.setLength(0);
        b.append(sel.scope).append("-").append(id);
        return b.toString();
    }
}
