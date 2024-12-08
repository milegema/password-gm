package com.bitwormhole.passwordgm.config;

import com.bitwormhole.passwordgm.boot.AutoAppKeyLoader;
import com.bitwormhole.passwordgm.boot.AutoUserKeyLoader;
import com.bitwormhole.passwordgm.boot.ComContextAgent;
import com.bitwormhole.passwordgm.components.ComponentManager;
import com.bitwormhole.passwordgm.components.ComponentSetBuilder;
import com.bitwormhole.passwordgm.components.ComponentSet;
import com.bitwormhole.passwordgm.contexts.ContextAgent;
import com.bitwormhole.passwordgm.contexts.ContextCustomizer;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeyPairManagerImpl;
import com.bitwormhole.passwordgm.security.SecretKeyManager;
import com.bitwormhole.passwordgm.security.SecretKeyManagerImpl;
import com.bitwormhole.passwordgm.utils.Logs;

public class ConfigComponents implements ContextCustomizer {

    public ConfigComponents() {
    }

    private static class All {

        KeyPairManagerImpl keyPairManager;
        SecretKeyManagerImpl secretKeyManager;
        AutoAppKeyLoader appKeyLoader;
        AutoUserKeyLoader userKeyLoader;
        ComContextAgent contextAgent;


        public All() {
            this.keyPairManager = new KeyPairManagerImpl();
            this.secretKeyManager = new SecretKeyManagerImpl();
            this.appKeyLoader = new AutoAppKeyLoader();
            this.userKeyLoader = new AutoUserKeyLoader();
            this.contextAgent = new ComContextAgent();
        }
    }


    private void create(ContextHolder ch, All all) {

        final ComponentSetBuilder builder = new ComponentSetBuilder();

        builder.addComponent(all.contextAgent).addAlias(ContextAgent.class);
        builder.addComponent(all.keyPairManager).addAlias(KeyPairManager.class);
        builder.addComponent(all.secretKeyManager).addAlias(SecretKeyManager.class);
        builder.addComponent(all.appKeyLoader);
        builder.addComponent(all.userKeyLoader);

        final ComponentSet cs = builder.create();
        ch.getApp().setComponents(cs);
    }

    private void wire(ContextHolder ch, All all) {

        ComponentManager cm = ch.getApp().getComponents();
        KeyPairManagerImpl kpm = cm.find(KeyPairManagerImpl.class);
        SecretKeyManagerImpl skm = cm.find(SecretKeyManagerImpl.class);

        Logs.debug("kpm = " + kpm);
        Logs.debug("skm = " + skm);


        wire(ch, all, all.contextAgent);
    }


    private void wire(ContextHolder ch, All all, ComContextAgent com) {
        com.setHolder(ch);
    }


    @Override
    public void customize(ContextHolder ch) {
        final All all = new All();
        create(ch, all);
        wire(ch, all);
    }
}
