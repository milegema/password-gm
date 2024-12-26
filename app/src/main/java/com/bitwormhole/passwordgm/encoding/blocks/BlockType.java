package com.bitwormhole.passwordgm.encoding.blocks;

public enum BlockType {

    ////////////////////////////////
    // base

    BLOB,
    Properties,
    Table,
    FooBar,

    ////////////////////////////////
    // keys

    SecretKey,
    KeyPair,

    ////////////////////////////////
    // blocks

    Root,
    App,
    User,
    Domain,
    Account,
    Scene,
    Passcode,
}
