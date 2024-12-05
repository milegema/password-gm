package com.bitwormhole.passwordgm.contexts;

public class DomainContext extends ContextBase {

    private String domain;

    public DomainContext(UserContext _parent) {
        super(_parent, ContextScope.DOMAIN);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
