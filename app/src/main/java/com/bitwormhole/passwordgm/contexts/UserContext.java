package com.bitwormhole.passwordgm.contexts;

public class UserContext extends ContextBase {

    private String email;

    public UserContext(AppContext _parent) {
        super(_parent, ContextScope.USER);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
