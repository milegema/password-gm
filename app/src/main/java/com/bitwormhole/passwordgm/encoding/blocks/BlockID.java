package com.bitwormhole.passwordgm.encoding.blocks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.security.SHA256SUM;


/**
 * BlockID 是一个 256 位的哈希值
 */
public final class BlockID {

    private final SHA256SUM sum;

    public BlockID() {
        this.sum = new SHA256SUM();
    }

    public BlockID(String str) {
        this.sum = new SHA256SUM(str);
    }

    public BlockID(byte[] bin) {
        this.sum = new SHA256SUM(bin);
    }


    private final static int LENGTH_IN_BIT = 256;
    private final static int LENGTH_IN_BYTE = LENGTH_IN_BIT / 8;
    private final static int LENGTH_IN_CHAR = LENGTH_IN_BYTE * 2;
    private final static int NUM_STEP = 8;


    public byte[] toByteArray() {
        return this.sum.toByteArray();
    }

    @Override
    public int hashCode() {
        return this.sum.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o2) {
        if (o2 == null) {
            return false;
        }
        if (o2 == this) {
            return true;
        }
        if (o2 instanceof BlockID) {
            final BlockID other = (BlockID) o2;
            return this.sum.equals(other.sum);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return sum.toString();
    }
}
