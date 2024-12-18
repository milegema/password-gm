package com.bitwormhole.passwordgm.data.ids;

public class UserAlias extends Alias {

    public UserAlias(String v) {
        super(v);
    }

    public UserAlias(EmailAddress email) {
        super(stringifyEmailAddress(email));
    }


    private static String stringifyEmailAddress(EmailAddress email) {
        String str = email.toString();
        return str.replaceFirst("@", "_(at)_");
    }
}
