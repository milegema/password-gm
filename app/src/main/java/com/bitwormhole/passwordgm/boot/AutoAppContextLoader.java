package com.bitwormhole.passwordgm.boot;

import android.content.Context;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;
import com.bitwormhole.passwordgm.data.blocks.AppBlock;
import com.bitwormhole.passwordgm.data.repositories.RepositoryConfig;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.Logs;
import com.bitwormhole.passwordgm.utils.Time;

import java.io.IOException;

public class AutoAppContextLoader implements ComLifecycle {

    private ContextHolder contextHolder;

    public AutoAppContextLoader() {
    }

    public ContextHolder getContextHolder() {
        return contextHolder;
    }

    public void setContextHolder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }


    @Override
    public ComLife life() {
        ComLife l = new ComLife();
        l.setOrder(BootOrder.APP_CONTEXT);
        l.setOnCreate(this::loadAppContext);
        l.setLoop(this::run_loop);
        return l;
    }

    private void run_loop() {
        AppContext app = this.contextHolder.getApp();
        for (; ; ) {
            if (app.isStopping()) {
                break;
            }
            if (app.isStopped()) {
                break;
            }
            Time.sleep(5000);
        }
    }

    private void loadAppContext() throws IOException {

        Logs.info("boot:loadAppContext");

        final ContextHolder ch = this.contextHolder;
        AppContext ac = ch.getApp();
        RootContext rc = ch.getRoot();
        Context android_app_ctx = ch.getAndroid().getApplicationContext();
        String android_app_name = android_app_ctx.getClass().getName();

        if (ac == null) {
            ac = ContextFactory.createAppContext(ch);
            ch.setApp(ac);
        }

        ac.setRepository(rc.getRepository());
        ac.setFolder(rc.getFolder().resolve(android_app_name));
        ac.setKeyPair(rc.getKeyPair());
        ac.setSecretKey(rc.getSecretKey());
        ac.setName(android_app_name);
        ac.setLabel("password-gm-app");
        ac.setAlias(android_app_ctx.getClass().getSimpleName());
        ac.setDescription("");
        ac.setBlock(this.loadAppBlock(ac));
    }

    private AppBlock loadAppBlock(AppContext ac) throws IOException {

        RepositoryConfig config = ac.getRepository().config();
        PropertyTable pt = config.loadProperties();

        return null;
    }
}
