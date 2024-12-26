package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.components.ComponentManager;
import com.bitwormhole.passwordgm.data.blocks.AccountBlock;
import com.bitwormhole.passwordgm.data.blocks.AppBlock;
import com.bitwormhole.passwordgm.network.web.WebClient;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.SecretKeyManager;
import com.bitwormhole.passwordgm.utils.Attributes;

import java.util.Properties;

public class AppContext extends ContextBase {

    private ComponentManager components;
    private Properties properties;
    private Attributes attributes;
    private KeyPairManager keyPairManager;
    private AppBlock block;
    private WebClient webClient;


    private boolean started;
    private boolean starting;
    private boolean stopped;
    private boolean stopping;
    private boolean developerMode;


    public AppContext(RootContext _parent) {
        super(_parent, ContextScope.APP);
        this.attributes = new Attributes();
        this.properties = new Properties();
    }

    public AppBlock getBlock() {
        return block;
    }

    public void setBlock(AppBlock block) {
        this.block = block;
    }

    public ComponentManager getComponents() {
        return components;
    }

    public void setComponents(ComponentManager components) {
        this.components = components;
    }

    public KeyPairManager getKeyPairManager() {
        return keyPairManager;
    }

    public void setKeyPairManager(KeyPairManager keyPairManager) {
        this.keyPairManager = keyPairManager;
    }


    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStopping() {
        return stopping;
    }

    public void setStopping(boolean stopping) {
        this.stopping = stopping;
    }

    public boolean isDeveloperMode() {
        return developerMode;
    }

    public void setDeveloperMode(boolean developerMode) {
        this.developerMode = developerMode;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
