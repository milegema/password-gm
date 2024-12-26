package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.DomainBlockID;
import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class DomainBlock extends BlockBase {


    private DomainBlockID id;
    private UserBlockID parent;


    public DomainBlock() {
        super(BlockType.Domain);
    }

}
