package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.utils.Logs;

public class AutoUserKeyLoader implements ComLifecycle {

    public AutoUserKeyLoader() {
    }

    @Override
    public ComLife life() {
        ComLife l = new ComLife();
        l.setOrder(0);
        l.setOnCreate(this::init);
        return l;
    }

    private void init() {

        Logs.debug("@init");

    }

}
