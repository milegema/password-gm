package com.bitwormhole.passwordgm.config;

import com.bitwormhole.passwordgm.boot.AutoAppKeyLoader;
import com.bitwormhole.passwordgm.boot.AutoUserKeyLoader;
import com.bitwormhole.passwordgm.components.ComponentManager;
import com.bitwormhole.passwordgm.components.ComponentSetBuilder;
import com.bitwormhole.passwordgm.components.ComponentSet;
import com.bitwormhole.passwordgm.contexts.ContextCustomizer;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeyPairManagerImpl;
import com.bitwormhole.passwordgm.security.SecretKeyManager;
import com.bitwormhole.passwordgm.security.SecretKeyManagerImpl;
import com.bitwormhole.passwordgm.utils.Logs;

public class ConfigComponents implements ContextCustomizer {


    private void create(ContextHolder ch) {
        final ComponentSetBuilder builder = new ComponentSetBuilder();

        builder.addComponent(new KeyPairManagerImpl()).addAlias(KeyPairManager.class);
        builder.addComponent(new SecretKeyManagerImpl()).addAlias(SecretKeyManager.class);
        builder.addComponent(new AutoAppKeyLoader());
        builder.addComponent(new AutoUserKeyLoader());

        final ComponentSet cs = builder.create();
        ch.getApp().setComponents(cs);
    }

    private void wire(ContextHolder ch) {

        ComponentManager cm = ch.getApp().getComponents();

        KeyPairManagerImpl kpm = cm.find(KeyPairManagerImpl.class);
        SecretKeyManagerImpl skm = cm.find(SecretKeyManagerImpl.class);


        Logs.debug("kpm = " + kpm);
        Logs.debug("skm = " + skm);
    }

    @Override
    public void customize(ContextHolder ch) {
        create(ch);
        wire(ch);
    }
}
