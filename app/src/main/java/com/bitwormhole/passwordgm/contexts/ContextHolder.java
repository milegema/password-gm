package com.bitwormhole.passwordgm.contexts;

import android.content.Context;

public class ContextHolder {

    private Context android;

    private RootContext root;
    private AppContext app;
    private UserContext user;
    private DomainContext domain;
    private AccountContext account;
    private SceneContext scene;
    private PasswordContext password;

    public ContextHolder() {
    }

    public AppContext getApp() {
        return app;
    }

    public void setApp(AppContext app) {
        this.app = app;
    }

    public UserContext getUser() {
        return user;
    }

    public void setUser(UserContext user) {
        this.user = user;
    }

    public DomainContext getDomain() {
        return domain;
    }

    public void setDomain(DomainContext domain) {
        this.domain = domain;
    }

    public AccountContext getAccount() {
        return account;
    }

    public void setAccount(AccountContext account) {
        this.account = account;
    }

    public RootContext getRoot() {
        return root;
    }

    public void setRoot(RootContext root) {
        this.root = root;
    }

    public Context getAndroid() {
        return android;
    }

    public void setAndroid(Context android) {
        this.android = android;
    }


    public SceneContext getScene() {
        return scene;
    }

    public void setScene(SceneContext scene) {
        this.scene = scene;
    }

    public PasswordContext getPassword() {
        return password;
    }

    public void setPassword(PasswordContext password) {
        this.password = password;
    }
}
