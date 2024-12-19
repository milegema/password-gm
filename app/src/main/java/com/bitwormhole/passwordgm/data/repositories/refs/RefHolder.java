package com.bitwormhole.passwordgm.data.repositories.refs;

public interface RefHolder extends Ref {

    RefFile toFile();

    RefFolder toFolder();

    boolean isFile();

    boolean isFolder();

}
