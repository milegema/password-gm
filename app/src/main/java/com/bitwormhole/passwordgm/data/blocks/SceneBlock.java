package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AccountBlockID;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.SceneBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class SceneBlock extends BlockBase {

    private SceneBlockID id;
    private AccountBlockID parent;

    public SceneBlock() {
        super(BlockType.Scene);
    }

    @Override
    public BlockID getParentBlockID() {
        return AccountBlockID.toBlockID(this.parent);
    }


    public SceneBlockID getId() {
        return id;
    }

    public void setId(SceneBlockID id) {
        this.id = id;
    }

    public AccountBlockID getParent() {
        return parent;
    }

    public void setParent(AccountBlockID parent) {
        this.parent = parent;
    }
}
