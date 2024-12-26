package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.RootBlockID;
import com.bitwormhole.passwordgm.data.properties.Names;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;

public final class RootBlockLS {


    public static RootBlock load(RootBlockID root_block_id, PropertyTable src) throws IOException {

        PropertyGetter getter = new PropertyGetter(src);
        RootBlock block = new RootBlock();
        getter.setRequired(false);

        BlockType type = getter.getBlockType(Names.block_type, null);
        RefName ref = getter.getRefName(Names.block_ref, null);
        String name = getter.getString(Names.block_name, "");
        long created_at = getter.getLong(Names.block_created_at, 0);
        byte[] salt = getter.getDataAuto(Names.block_salt, null);
        BlockID parent_id = getter.getBlockID(Names.block_parent, null);

        block.setType(type);
        block.setName(name);
        block.setCreatedAt(created_at);
        block.setSalt(salt);
        block.setRef(ref);
        block.setParent(parent_id);

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


        setter.put(Names.block_type, block.getType());
        setter.put(Names.block_name, block.getName());
        setter.put(Names.block_created_at, block.getCreatedAt());
        setter.put(Names.block_salt, block.getSalt());
        setter.put(Names.block_ref, block.getRef());
        setter.put(Names.block_parent, block.getParent());

        dst.importAll(tmp.exportAll(null));
    }
}
