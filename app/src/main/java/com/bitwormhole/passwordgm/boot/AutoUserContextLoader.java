package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextAgent;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;
import com.bitwormhole.passwordgm.contexts.UserContext;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutoUserContextLoader implements ComLifecycle {

    private ContextHolder contextHolder;

    public AutoUserContextLoader() {
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
        l.setOrder(BootOrder.USER_CONTEXT);
        l.setOnCreate(this::loadAll);
        return l;
    }


    // private

    private interface PrivateLoaderFunc {
        UserContext invoke(UserContext uc) throws IOException;
    }

    private void loadAll() throws IOException {

        Logs.info("boot:loadUserContext");

        final List<PrivateLoaderFunc> steps = new ArrayList<>();
        final ContextHolder ch = this.contextHolder;
        UserContext uc = ch.getUser();

        steps.add(this::loadUserContext);
        steps.add(this::loadUserConfig);

        for (PrivateLoaderFunc fn : steps) {
            uc = fn.invoke(uc);
        }

        ch.setUser(uc);
    }


    private UserContext loadUserConfig(UserContext uc) throws IOException {


        return uc;
    }


    private UserContext loadUserContext(UserContext uc) {

        final ContextHolder ch = this.contextHolder;

        if (uc == null) {
            uc = ContextFactory.createUserContext("todo", ch);
            ch.setUser(uc);
        }

        return uc;
    }
}
