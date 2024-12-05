package com.bitwormhole.passwordgm.config;

import com.bitwormhole.passwordgm.contexts.ContextCustomizer;
import com.bitwormhole.passwordgm.contexts.ContextHolder;

import java.util.Properties;

public class ConfigProperties implements ContextCustomizer {
    @Override
    public void customize(ContextHolder ch) {
        Properties p = ch.getApp().getProperties();
        p.setProperty("a", "1");
        p.setProperty("b", "2");
        p.setProperty("c", "3");
    }
}
