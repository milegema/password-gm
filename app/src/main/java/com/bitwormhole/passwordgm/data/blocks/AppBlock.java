package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AppBlockID;
import com.bitwormhole.passwordgm.data.ids.RootBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class AppBlock extends BlockBase {

    private AppBlockID id;
    private RootBlockID parent;

    public AppBlock() {
        super(BlockType.App);
    }
}
