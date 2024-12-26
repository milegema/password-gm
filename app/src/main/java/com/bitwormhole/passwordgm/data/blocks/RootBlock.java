package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.RootBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class RootBlock extends BlockBase {

    private RootBlockID id;

    public RootBlock() {
        super(BlockType.Root);
    }


    public RootBlockID getId() {
        return id;
    }

    public void setId(RootBlockID id) {
        this.id = id;
    }
}
