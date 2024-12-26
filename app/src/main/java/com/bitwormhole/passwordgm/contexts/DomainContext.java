package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.data.blocks.AccountBlock;
import com.bitwormhole.passwordgm.data.blocks.DomainBlock;

public class DomainContext extends ContextBase {

    private String domain;

    private DomainBlock block;


    public DomainContext(UserContext _parent) {
        super(_parent, ContextScope.DOMAIN);
    }

    public DomainBlock getBlock() {
        return block;
    }

    public void setBlock(DomainBlock block) {
        this.block = block;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
