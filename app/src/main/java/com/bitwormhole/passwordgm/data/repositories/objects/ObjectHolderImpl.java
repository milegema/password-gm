package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;

import java.nio.file.Files;

final class ObjectHolderImpl implements ObjectHolder {

    private final ObjectContext mContext;

    public ObjectHolderImpl(ObjectContext oc) {
        this.mContext = oc;
    }

    @Override
    public ObjectID id() {
        return mContext.getId();
    }

    @Override
    public boolean exists() {
        return Files.exists(mContext.getFile());
    }

    @Override
    public ObjectReader reader() {
        return mContext.getReader();
    }
}
