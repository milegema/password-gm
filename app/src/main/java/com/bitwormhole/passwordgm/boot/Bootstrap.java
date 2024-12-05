package com.bitwormhole.passwordgm.boot;

import android.content.Context;

import com.bitwormhole.passwordgm.PasswordGMApp;
import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifeContext;
import com.bitwormhole.passwordgm.components.ComLifeManager;
import com.bitwormhole.passwordgm.components.ComLifeRunner;
import com.bitwormhole.passwordgm.components.ComLifeScanner;
import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;
import com.bitwormhole.passwordgm.contexts.UserContext;

public final class Bootstrap {

    private Bootstrap() {
    }

    public static void boot(Context ctx) {

        Bootstrap b = new Bootstrap();
        PasswordGMApp app = (PasswordGMApp) ctx.getApplicationContext();
        ContextHolder contexts = app.getContexts();

        b.initRoot(contexts);

        b.initApp(contexts);
        b.doCreateComponents(contexts);
        b.doStartComponents(contexts);

        b.initUser(contexts);
    }

    // private methods

    private void initRoot(ContextHolder holder) {
        RootContext root = holder.getRoot();
        if (root != null) {
            return;
        }
        root = ContextFactory.createRootContext(holder);
        holder.setRoot(root);
    }

    private void initApp(ContextHolder holder) {
        AppContext ac = holder.getApp();
        if (ac != null) {
            return;
        }
        ac = ContextFactory.createAppContext(holder);
        holder.setApp(ac);
    }

    private void doCreateComponents(ContextHolder holder) {
        holder.getRoot().getContextCustomizer().customize(holder);
    }

    private void doStartComponents(ContextHolder holder) {

        ComLifeContext ctx = new ComLifeContext();
        ComLifeScanner.scan(ctx, holder);
        ComLifeManager clm = new ComLifeManager(ctx);
        ComLife life = clm.getMain();
        ComLifeRunner runner = new ComLifeRunner(ctx, life);

        runner.start();
        runner.waitUtilStarted(15 * 1000);
    }


    private void initUser(ContextHolder holder) {
        UserContext uc = holder.getUser();
        if (uc != null) {
            return;
        }
        uc = ContextFactory.createUserContext("todo:name", holder);
        holder.setUser(uc);
    }
}
