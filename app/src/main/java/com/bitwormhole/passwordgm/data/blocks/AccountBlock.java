package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AccountBlockID;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.DomainBlockID;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;

public class AccountBlock extends BlockBase {

    private AccountBlockID id;
    private DomainBlockID parent;

    public AccountBlock() {
        super(BlockType.Account);
    }

    @Override
    public BlockID getParentBlockID() {
        return DomainBlockID.toBlockID(this.parent);
    }

    public AccountBlockID getId() {
        return id;
    }

    public void setId(AccountBlockID id) {
        this.id = id;
    }

    public DomainBlockID getParent() {
        return parent;
    }

    public void setParent(DomainBlockID parent) {
        this.parent = parent;
    }
}
