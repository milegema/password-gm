package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.data.blocks.RootBlock;
import com.bitwormhole.passwordgm.data.properties.pdo.ConfigPDO;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.SecretKeyManager;

public class RootContext extends ContextBase {

    private ContextCustomizer contextCustomizer;

    private RootBlock block;
    private ConfigPDO config;
    private PropertyTable configProperties;

    public RootContext() {
        super(null, ContextScope.ROOT);
    }

    public RootBlock getBlock() {
        return block;
    }

    public void setBlock(RootBlock block) {
        this.block = block;
    }

    public ContextCustomizer getContextCustomizer() {
        return contextCustomizer;
    }

    public void setContextCustomizer(ContextCustomizer contextCustomizer) {
        this.contextCustomizer = contextCustomizer;
    }

    public ConfigPDO getConfig() {
        return config;
    }

    public void setConfig(ConfigPDO config) {
        this.config = config;
    }

    public PropertyTable getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(PropertyTable configProperties) {
        this.configProperties = configProperties;
    }
}
