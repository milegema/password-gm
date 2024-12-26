package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AppBlockID;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.properties.Names;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectEntity;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectHolder;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;

public final class AppBlockLS {

    private AppBlockLS() {
    }


    public static AppBlock load(ObjectID id, PropertyTable src) throws IOException {

        PropertyGetter getter = new PropertyGetter(src);
        getter.setRequired(false);
        AppBlock block = new AppBlock();

        block.setParent(null);
        block.setId(new AppBlockID(ObjectID.convert(id)));
        BlockBaseLS.load(getter, block);

        return block;
    }

    public static AppBlock load(ObjectID id, Repository src) throws IOException {
        ObjectHolder h_obj = src.objects().get(id);
        ObjectEntity entity = h_obj.reader().read();
        PropertyTable pt = PropertyTableLS.decode(entity.getContent().toByteArray());
        return load(id, pt);
    }

    public static AppBlockID store(AppBlock block, Repository dst) throws IOException {
        PropertyTable pt = PropertyTable.Factory.create();
        store(block, pt);
        byte[] data = PropertyTableLS.encode(pt);
        ObjectEntity entity = new ObjectEntity();

        entity.setId(null);
        entity.setType(BlockType.App);
        entity.setContent(new ByteSlice(data));

        entity = dst.objects().writer().write(entity);
        ObjectID id = entity.getId();
        return new AppBlockID(ObjectID.convert(id));
    }

    public static void store(AppBlock block, PropertyTable dst) throws IOException {
        PropertySetter setter = new PropertySetter(dst);
        BlockBaseLS.store(block, setter);
        setter.put(Names.block_parent, block.getParent());
    }
}
