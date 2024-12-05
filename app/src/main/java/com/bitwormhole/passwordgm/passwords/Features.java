package com.bitwormhole.passwordgm.passwords;

public final class Features {


    public static final long PIN = 0x0000;
    public static final long REVISION = 0x0000;


    private long bits;

    public Features() {
    }

    public Features add(long bit) {
        this.bits |= bit;
        return this;
    }

    public Features remove(long bit) {
        this.bits &= (~bit);
        return this;
    }

    public boolean has(long bit) {
        return (this.bits & bit) != 0;
    }
}
