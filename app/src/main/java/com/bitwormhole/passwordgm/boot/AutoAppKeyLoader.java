package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.utils.Logs;

public class AutoAppKeyLoader implements ComLifecycle {

    public AutoAppKeyLoader() {
    }

    @Override
    public ComLife life() {
        ComLife l = new ComLife();
        l.setOrder(0);
        l.setOnCreate(this::loadAppKeys);
        return l;
    }


    private void loadAppKeys() {
        //  AppKeyLoader loader = new AppKeyLoader(holder);
        // loader.load();

        Logs.debug("@loadAppKeys");
    }

}
