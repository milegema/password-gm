package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;

public interface ObjectHolder {

    ObjectID id();

    boolean exists();

    ObjectReader reader();
}
