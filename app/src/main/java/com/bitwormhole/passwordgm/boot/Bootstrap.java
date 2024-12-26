package com.bitwormhole.passwordgm.boot;

import android.content.Context;

import com.bitwormhole.passwordgm.PgmAppInterface;
import com.bitwormhole.passwordgm.PgmApplication;
import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifeContext;
import com.bitwormhole.passwordgm.components.ComLifeManager;
import com.bitwormhole.passwordgm.components.ComLifeRunner;
import com.bitwormhole.passwordgm.components.ComLifeScanner;
import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;

public final class Bootstrap {

    private Bootstrap() {
    }

    public static ContextHolder boot(Context ctx) {
        PgmAppInterface app = (PgmAppInterface) ctx.getApplicationContext();
        ContextHolder ch = app.getContexts();
        try {
            Bootstrap b = new Bootstrap();
            b.doInit(ch);
            b.doCreateComponents(ch);
            b.doStartComponents(ch);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException(e);
        }
        return ch;
    }

    private void doInit(ContextHolder ch) {

        RootContext root = ch.getRoot();
        AppContext app = ch.getApp();
        PgmApplication pgm_app = (PgmApplication) ch.getAndroid().getApplicationContext();

        if (root == null) {
            root = ContextFactory.createRootContext(ch);
            ch.setRoot(root);
        }
        if (app == null) {
            app = ContextFactory.createAppContext(ch);
            ch.setApp(app);
        }

        root.setContextCustomizer(pgm_app);
    }

    // private methods


    private void doCreateComponents(ContextHolder holder) {
        holder.getRoot().getContextCustomizer().customize(holder);
    }

    private void doStartComponents(ContextHolder holder) {

        ComLifeContext ctx = new ComLifeContext();
        ComLifeScanner.scan(ctx, holder);
        ComLifeManager clm = new ComLifeManager(ctx);

        ComLife life1 = clm.getMain();
        ComLife life2 = new ComLife(life1);
        // life2.setLoop(null);
        // life2.setOnStop(null);
        // life2.setOnDestroy(null);

        ComLifeRunner runner = new ComLifeRunner(ctx, life2);
        runner.start();
        runner.waitUtilStarted(30 * 1000);
    }
}
