package com.bitwormhole.passwordgm.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BlockIDWrapper {

    private final BlockID id;

    public BlockIDWrapper(BlockID inner) {
        this.id = new BlockID(inner);
    }

    public BlockID toBlockID() {
        return this.id;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof BlockIDWrapper) {
            BlockIDWrapper o2 = (BlockIDWrapper) other;
            return this.id.equals(o2.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this.id.toString();
    }
}
