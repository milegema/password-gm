package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.DomainBlockID;
import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class DomainBlock extends BlockBase {


    private DomainBlockID id;
    private UserBlockID parent;


    public DomainBlock() {
        super(BlockType.Domain);
    }

    @Override
    public BlockID getParentBlockID() {
        return UserBlockID.toBlockID(this.parent);
    }

    public DomainBlockID getId() {
        return id;
    }

    public void setId(DomainBlockID id) {
        this.id = id;
    }

    public UserBlockID getParent() {
        return parent;
    }

    public void setParent(UserBlockID parent) {
        this.parent = parent;
    }
}
