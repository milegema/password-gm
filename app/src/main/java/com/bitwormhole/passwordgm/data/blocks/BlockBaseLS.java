package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.properties.Names;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.util.Map;

final class BlockBaseLS {


    public static void load(PropertyGetter src, BlockBase block) {

        block.setCreatedAt(src.getLong(Names.block_created_at, 0));
        block.setName(src.getString(Names.block_name, null));
        block.setRef(src.getRefName(Names.block_ref, null));
        block.setSalt(src.getData(Names.block_salt, null));
        block.setType(src.getBlockType(Names.block_type, null));

        block.setProperties(src.getProperties());
    }

    public static void store(BlockBase block, PropertySetter dst) {

        PropertyTable src = block.getProperties();
        if (src != null) {
            Map<String, String> table = src.exportAll(null);
            table.forEach(dst::put);
        }

        dst.put(Names.block_created_at, block.getCreatedAt());
        dst.put(Names.block_name, block.getName());
        dst.put(Names.block_ref, block.getRef());
        dst.put(Names.block_salt, block.getSalt());
        dst.put(Names.block_type, block.getType());
    }
}
