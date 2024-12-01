package com.bitwormhole.passwordgm.contexts;

import java.io.File;

public class ContextFactory {


    public static AppContext createAppContext(ContextHolder ch) {
        RootContext parent = ch.getRoot();
        AppContext child = new AppContext(parent);
        child.setName(".password-gm");
        return child;
    }

    public static UserContext createUserContext(String name, ContextHolder ch) {
        AppContext parent = ch.getApp();
        UserContext child = new UserContext(parent);
        child.setName(name);
        return child;
    }

    public static DomainContext createDomainContext(String name, ContextHolder ch) {
        UserContext parent = ch.getUser();
        DomainContext child = new DomainContext(parent);
        child.setName(name);
        return child;
    }

    public static AccountContext createAccountContext(String name, ContextHolder ch) {
        DomainContext parent = ch.getDomain();
        AccountContext child = new AccountContext(parent);
        child.setName(name);
        child.setKeyPair(parent.getKeyPair());
        child.setSecretKey(parent.getSecretKey());
        return child;
    }

    public static RootContext createRootContext(ContextHolder ch) {
        File dir = ch.getAndroid().getDataDir();
        RootContext ctx = new RootContext();
        ctx.setName("root");
        ctx.setLabel("ROOT");
        ctx.setDescription("password_gm_app_root");
        ctx.setFolder(dir.toPath());
        return ctx;
    }
}
