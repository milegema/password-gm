package com.bitwormhole.passwordgm;

import android.app.Application;

import com.bitwormhole.passwordgm.contexts.ContextHolder;

public class PgmApplication extends Application implements PasswordGMApp {

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
        ContextHolder holder = new ContextHolder();
        holder.setAndroid(this);
        return holder;
    }
}
