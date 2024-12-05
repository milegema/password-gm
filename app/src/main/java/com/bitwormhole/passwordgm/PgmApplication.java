package com.bitwormhole.passwordgm;

import android.app.Application;

import com.bitwormhole.passwordgm.config.ConfigComponents;
import com.bitwormhole.passwordgm.config.ConfigProperties;
import com.bitwormhole.passwordgm.contexts.ContextCustomizer;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;

import java.util.ArrayList;
import java.util.List;

public class PgmApplication extends Application implements PasswordGMApp, ContextCustomizer {

    private ContextHolder mContextHolder;

    @Override
    public ContextHolder getContexts() {
        ContextHolder holder = mContextHolder;
        if (holder == null) {
            holder = loadContexts();
            mContextHolder = holder;
        }
        return holder;
    }

    private ContextHolder loadContexts() {
        // make holder
        ContextHolder holder = new ContextHolder();
        holder.setAndroid(this);
        // make root
        RootContext root = ContextFactory.createRootContext(holder);
        root.setContextCustomizer(this);
        holder.setRoot(root);
        return holder;
    }

    @Override
    public void customize(ContextHolder ch) {
        List<ContextCustomizer> list = new ArrayList<>();
        list.add(new ConfigProperties());
        list.add(new ConfigComponents());
        for (ContextCustomizer cc : list) {
            cc.customize(ch);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
