package com.bitwormhole.passwordgm.data.properties.pdo;


import com.bitwormhole.passwordgm.data.properties.Names;
import com.bitwormhole.passwordgm.data.properties.pio.UserPIO;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;


public final class ConfigLS {

    private ConfigLS() {
    }

    public static ConfigPDO load(PropertyTable src) throws IOException {
        ConfigPDO dst = new ConfigPDO();
        if (src == null) {
            return dst;
        }
        PropertyGetter getter = new PropertyGetter(src);
        getter.setRequired(false);
        dst.setRefsBlocksApp(getter.getRefName(Names.refs_blocks_app, null));
        dst.setRefsBlocksRoot(getter.getRefName(Names.refs_blocks_root, null));
        dst.setRefsBlocksUser(getter.getRefName(Names.refs_blocks_user, null));
        return dst;
    }

    public static void store(ConfigPDO pdo, PropertyTable dst) throws IOException {
        if (pdo == null || dst == null) {
            return;
        }
        PropertySetter setter = new PropertySetter(dst);
        setter.put(Names.refs_blocks_app, pdo.getRefsBlocksApp());
        setter.put(Names.refs_blocks_root, pdo.getRefsBlocksRoot());
        setter.put(Names.refs_blocks_user, pdo.getRefsBlocksUser());
    }
}
