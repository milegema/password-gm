package com.bitwormhole.passwordgm.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.errors.PGMException;


/**
 * ObjectID 是对 BlockID 的简单包装,  是一种特殊的 BlockID
 */
public final class ObjectID {

    private final BlockID _block_id;

    private ObjectID(BlockID id) {
        this._block_id = id;
    }


    public static ObjectID convert(BlockID src) {
        if (src == null) {
            throw new PGMException("block-id is null");
        }
        return new ObjectID(src);
    }

    public static BlockID convert(ObjectID src) {
        return src._block_id;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof ObjectID) {
            ObjectID o2 = (ObjectID) obj;
            return this._block_id.equals(o2._block_id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this._block_id.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this._block_id.toString();
    }
}
