package com.bitwormhole.passwordgm.data.tables;

import com.bitwormhole.passwordgm.data.ids.PasswordBlockID;
import com.bitwormhole.passwordgm.data.ids.SceneBlockID;

public class SceneEntity extends EntityBase {

    private SceneBlockID sceneBID; // this scene block-id
    private PasswordBlockID passwordBID; // current password block-id


    private String user;
    private String domain;
    private String account;
    private String scene;

    public SceneEntity() {
    }

    public SceneBlockID getSceneBID() {
        return sceneBID;
    }

    public void setSceneBID(SceneBlockID sceneBID) {
        this.sceneBID = sceneBID;
    }

    public PasswordBlockID getPasswordBID() {
        return passwordBID;
    }

    public void setPasswordBID(PasswordBlockID passwordBID) {
        this.passwordBID = passwordBID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }
}
