package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

public class BlockBase {

    private BlockType type;
    private String name;
    private RefName ref;
    private long createdAt;
    private PropertyTable properties;
    private BlockID parent;
    private byte[] salt;

    public BlockBase(BlockType t) {
        this.type = t;
    }

    public RefName getRef() {
        return ref;
    }

    public void setRef(RefName ref) {
        this.ref = ref;
    }

    public PropertyTable getProperties() {
        return properties;
    }

    public void setProperties(PropertyTable properties) {
        this.properties = properties;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlockID getParent() {
        return parent;
    }

    public void setParent(BlockID parent) {
        this.parent = parent;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public static boolean isAvailable(BlockBase block) {
        if (block == null) {
            return false;
        }
        if (block.type == null) {
            return false;
        }
        return block.createdAt > 0;
    }
}
