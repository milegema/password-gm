package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AccountBlockID;
import com.bitwormhole.passwordgm.data.ids.DomainBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class AccountBlock extends BlockBase {

    private AccountBlockID id;
    private DomainBlockID parent;

    public AccountBlock() {
        super(BlockType.Account);
    }

}
