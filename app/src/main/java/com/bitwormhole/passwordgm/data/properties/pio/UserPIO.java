package com.bitwormhole.passwordgm.data.properties.pio;

/**
 * User Properties Item Object
 */
public class UserPIO {

    private String name;
    private String alias;
    private String email;

    public UserPIO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
