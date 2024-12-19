package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.utils.ByteSlice;

public class ObjectEntity {

    private ObjectID id;
    private BlockType type;
    private ByteSlice content;

    public ObjectEntity() {
    }

    public ObjectEntity(ObjectEntity src) {
        if (src == null) {
            return;
        }
        this.id = src.id;
        this.type = src.type;
        this.content = src.content;
    }

    public ObjectID getId() {
        return id;
    }

    public void setId(ObjectID id) {
        this.id = id;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public ByteSlice getContent() {
        return content;
    }

    public void setContent(ByteSlice content) {
        this.content = content;
    }
}
