package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.RootBlockID;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;

public final class RootBlockLS {


    public static RootBlock load(RootBlockID root_block_id, PropertyTable src) throws IOException {

        PropertyGetter getter = new PropertyGetter(src);
        RootBlock block = new RootBlock();
        getter.setRequired(false);

        BlockBaseLS.load(getter, block);
        block.setProperties(src);
        block.setId(root_block_id);
        return block;
    }


    public static void store(RootBlock block, PropertyTable dst) throws IOException {

        PropertyTable src = block.getProperties();
        PropertyTable tmp = PropertyTable.Factory.create();
        PropertySetter setter = new PropertySetter(tmp);

        if (src != null) {
            tmp.importAll(src.exportAll(null));
        }

        BlockBaseLS.store(block, setter);
        dst.importAll(tmp.exportAll(null));
    }
}
