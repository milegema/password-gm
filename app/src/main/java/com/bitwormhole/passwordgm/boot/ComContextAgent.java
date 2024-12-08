package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.contexts.ContextAgent;
import com.bitwormhole.passwordgm.contexts.ContextHolder;

public class ComContextAgent implements ContextAgent {

    private ContextHolder holder;

    public ComContextAgent() {
    }

    public ContextHolder getHolder() {
        return holder;
    }

    public void setHolder(ContextHolder holder) {
        this.holder = holder;
    }

    @Override
    public ContextHolder getContextHolder() {
        return this.holder;
    }
}
