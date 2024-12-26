package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AppBlockID;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.RootBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class AppBlock extends BlockBase {

    private AppBlockID id;
    private RootBlockID parent;

    public AppBlock() {
        super(BlockType.App);
    }

    public AppBlockID getId() {
        return id;
    }

    public void setId(AppBlockID id) {
        this.id = id;
    }


    public RootBlockID getParent() {
        return parent;
    }

    public void setParent(RootBlockID parent) {
        this.parent = parent;
    }

    @Override
    public BlockID getParentBlockID() {
        return RootBlockID.toBlockID(this.parent);
    }
}
