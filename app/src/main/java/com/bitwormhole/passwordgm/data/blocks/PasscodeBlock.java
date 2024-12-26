package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.PasswordBlockID;
import com.bitwormhole.passwordgm.data.ids.SceneBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class PasscodeBlock extends BlockBase {

    private PasswordBlockID id;
    private PasswordBlockID older;
    private SceneBlockID parent;

    public PasscodeBlock() {
        super(BlockType.Passcode);
    }


    public PasswordBlockID getId() {
        return id;
    }

    public void setId(PasswordBlockID id) {
        this.id = id;
    }

    public PasswordBlockID getOlder() {
        return older;
    }

    public void setOlder(PasswordBlockID older) {
        this.older = older;
    }

    public SceneBlockID getParent() {
        return parent;
    }

    public void setParent(SceneBlockID parent) {
        this.parent = parent;
    }

    @Override
    public BlockID getParentBlockID() {
        return SceneBlockID.toBlockID(this.parent);
    }
}
