package com.bitwormhole.passwordgm.data.repositories.refs;

public interface RefFolder extends Ref {

    String[] items();

    RefName[] listItemNames();

}
