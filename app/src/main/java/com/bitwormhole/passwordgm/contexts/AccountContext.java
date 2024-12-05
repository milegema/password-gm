package com.bitwormhole.passwordgm.contexts;

public class AccountContext extends ContextBase {

    private String username;

    public AccountContext(DomainContext _parent) {
        super(_parent, ContextScope.ACCOUNT);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
