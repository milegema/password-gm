package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AppBlockID;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class UserBlock extends BlockBase {

    private UserBlockID id;
    private AppBlockID parent;

    public UserBlock() {
        super(BlockType.User);
    }

    @Override
    public BlockID getParentBlockID() {
        return AppBlockID.toBlockID(this.parent);
    }

    public UserBlockID getId() {
        return id;
    }

    public void setId(UserBlockID id) {
        this.id = id;
    }

    public AppBlockID getParent() {
        return parent;
    }

    public void setParent(AppBlockID parent) {
        this.parent = parent;
    }
}
