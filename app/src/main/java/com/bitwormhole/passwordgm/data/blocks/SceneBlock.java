package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AccountBlockID;
import com.bitwormhole.passwordgm.data.ids.SceneBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class SceneBlock extends BlockBase {

    private SceneBlockID id;
    private AccountBlockID parent;

    public SceneBlock() {
        super(BlockType.Scene);
    }

}
