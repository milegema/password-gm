package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class ExampleBlock extends BlockBase {

    public ExampleBlock() {
        super(BlockType.FooBar);
    }

    @Override
    public BlockID getParentBlockID() {
        return null;
    }
}
