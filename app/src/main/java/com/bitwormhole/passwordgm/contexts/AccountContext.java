package com.bitwormhole.passwordgm.contexts;

public class AccountContext extends ContextBase {
    public AccountContext(DomainContext _parent) {
        super(_parent, ContextScope.ACCOUNT);
    }
}
