package com.bitwormhole.passwordgm.utils;

import java.util.Properties;

public class PropertyGetter {

    private Properties properties;
    private boolean required;

    public PropertyGetter() {
        this.properties = new Properties();
        this.required = true;
    }

    public PropertyGetter(Properties src) {
        if (src == null) {
            src = new Properties();
        }
        this.properties = src;
        this.required = true;
    }


    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties p) {
        if (p == null) {
            return;
        }
        this.properties = p;
    }
}
