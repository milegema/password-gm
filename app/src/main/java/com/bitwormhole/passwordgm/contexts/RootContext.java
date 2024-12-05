package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.SecretKeyManager;

public class RootContext extends ContextBase {

    private ContextCustomizer contextCustomizer;

    public RootContext() {
        super(null, ContextScope.ROOT);
    }

    public ContextCustomizer getContextCustomizer() {
        return contextCustomizer;
    }

    public void setContextCustomizer(ContextCustomizer contextCustomizer) {
        this.contextCustomizer = contextCustomizer;
    }
}
