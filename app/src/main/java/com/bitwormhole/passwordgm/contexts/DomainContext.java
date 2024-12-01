package com.bitwormhole.passwordgm.contexts;

public class DomainContext extends ContextBase {
    public DomainContext(UserContext _parent) {
        super(_parent, ContextScope.DOMAIN);
    }
}
