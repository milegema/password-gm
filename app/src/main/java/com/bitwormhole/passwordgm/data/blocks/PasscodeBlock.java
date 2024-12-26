package com.bitwormhole.passwordgm.data.blocks;

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

}
