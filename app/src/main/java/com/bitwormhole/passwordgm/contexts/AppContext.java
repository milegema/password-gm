package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.components.ComponentManager;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.SecretKeyManager;
import com.bitwormhole.passwordgm.utils.Attributes;

import java.util.Properties;

public class AppContext extends ContextBase {

    private ComponentManager components;
    private Properties properties;
    private Attributes attributes;

    private KeyPairManager keyPairManager;
    private SecretKeyManager secretKeyManager;

    public AppContext(RootContext _parent) {
        super(_parent, ContextScope.APP);
        this.attributes = new Attributes();
        this.properties = new Properties();
    }

    public ComponentManager getComponents() {
        return components;
    }

    public void setComponents(ComponentManager components) {
        this.components = components;
    }

    public SecretKeyManager getSecretKeyManager() {
        return secretKeyManager;
    }

    public void setSecretKeyManager(SecretKeyManager secretKeyManager) {
        this.secretKeyManager = secretKeyManager;
    }

    public KeyPairManager getKeyPairManager() {
        return keyPairManager;
    }

    public void setKeyPairManager(KeyPairManager keyPairManager) {
        this.keyPairManager = keyPairManager;
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
}
