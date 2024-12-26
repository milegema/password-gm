package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AppBlockID;
import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class UserBlock extends BlockBase {

    private UserBlockID id;
    private AppBlockID parent;

    public UserBlock() {
        super(BlockType.User);
    }

}
