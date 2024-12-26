package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.data.blocks.UserBlock;
import com.bitwormhole.passwordgm.data.properties.pdo.ConfigPDO;
import com.bitwormhole.passwordgm.data.properties.pio.UserPIO;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

public class UserContext extends ContextBase {

    private UserBlock block;
    private UserPIO user;
    private ConfigPDO config;
    private PropertyTable configProperties;


    public UserContext(AppContext _parent) {
        super(_parent, ContextScope.USER);
    }

    public static UserContext copy(UserContext src) {

        AppContext parent = (AppContext) src.getParent();
        UserContext dst = new UserContext(parent);

        dst.setFolder(src.getFolder());
        dst.setName(src.getName());
        dst.setAlias(src.getAlias());
        dst.setLabel(src.getLabel());
        dst.setDescription(src.getDescription());
        dst.setSecretKey(src.getSecretKey());
        dst.setKeyPair(src.getKeyPair());
        dst.setRepository(src.getRepository());

        dst.block = src.block;
        dst.config = src.config;
        dst.configProperties = src.configProperties;
        dst.user = src.user;

        return dst;
    }


    public UserBlock getBlock() {
        return block;
    }

    public void setBlock(UserBlock block) {
        this.block = block;
    }


    public ConfigPDO getConfig() {
        return config;
    }

    public void setConfig(ConfigPDO config) {
        this.config = config;
    }

    public UserPIO getUser() {
        return user;
    }

    public void setUser(UserPIO user) {
        this.user = user;
    }

    public PropertyTable getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(PropertyTable configProperties) {
        this.configProperties = configProperties;
    }
}
