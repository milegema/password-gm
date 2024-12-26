package com.bitwormhole.passwordgm.config;

import android.content.Context;

import com.bitwormhole.passwordgm.boot.AutoAppContextLoader;
import com.bitwormhole.passwordgm.boot.AutoRootContextLoader;
import com.bitwormhole.passwordgm.boot.AutoUserContextLoader;
import com.bitwormhole.passwordgm.boot.ComContextAgent;
import com.bitwormhole.passwordgm.components.ComponentManager;
import com.bitwormhole.passwordgm.components.ComponentSetBuilder;
import com.bitwormhole.passwordgm.components.ComponentSet;
import com.bitwormhole.passwordgm.contexts.ContextAgent;
import com.bitwormhole.passwordgm.contexts.ContextCustomizer;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.data.repositories.RepositoryManager;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.network.web.WebClientFacade;
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
        AutoRootContextLoader rootContextLoader;
        RepositoryManager repositoryManager;

        AutoAppContextLoader appContextLoader;
        AutoUserContextLoader userContextLoader;
        ComContextAgent contextAgent;
        WebClientFacade webClient;


        public All(Context ctx) {
            this.contextAgent = new ComContextAgent();
            this.keyPairManager = new KeyPairManagerImpl();
            this.repositoryManager = RepositoryManager.getInstance(ctx);

            this.rootContextLoader = new AutoRootContextLoader();
            this.appContextLoader = new AutoAppContextLoader();
            this.userContextLoader = new AutoUserContextLoader();

            this.webClient = new WebClientFacade();
        }
    }


    private void create(ContextHolder ch, All all) {

        final ComponentSetBuilder builder = new ComponentSetBuilder();

        builder.addComponent(all.contextAgent).addAlias(ContextAgent.class);
        builder.addComponent(all.keyPairManager).addAlias(KeyPairManager.class);
        builder.addComponent(all.repositoryManager).addAlias(RepositoryManager.class);
        builder.addComponent(all.webClient).addAlias(WebClient.class);

        builder.addComponent(all.rootContextLoader);
        builder.addComponent(all.appContextLoader);
        builder.addComponent(all.userContextLoader);

        final ComponentSet cs = builder.create();
        ch.getApp().setComponents(cs);
    }

    private void wire(ContextHolder ch, All all) {

        ComponentManager cm = ch.getApp().getComponents();
        KeyPairManagerImpl kpm = cm.find(KeyPairManagerImpl.class);


        Logs.debug("kpm = " + kpm);


        wire(ch, all, all.contextAgent);
        wire(ch, all, all.appContextLoader);
        wire(ch, all, all.rootContextLoader);
        wire(ch, all, all.userContextLoader);

    }


    private void wire(ContextHolder ch, All all, ComContextAgent com) {
        com.setHolder(ch);
    }


    private void wire(ContextHolder ch, All all, AutoRootContextLoader com) {

        com.setContextHolder(ch);
        com.setKpm(all.keyPairManager);
        com.setRepositoryManager(all.repositoryManager);
    }

    private void wire(ContextHolder ch, All all, AutoAppContextLoader com) {

        com.setContextHolder(ch);
    }

    private void wire(ContextHolder ch, All all, AutoUserContextLoader com) {

        com.setContextHolder(ch);


    }


    @Override
    public void customize(ContextHolder ch) {
        final All all = new All(ch.getAndroid());
        create(ch, all);
        wire(ch, all);
    }
}
