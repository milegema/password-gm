package com.bitwormhole.passwordgm.data.repositories.refs;

import com.bitwormhole.passwordgm.data.ids.ObjectID;

import java.io.IOException;

public interface Ref {

    RefName name();

    boolean exists();

    ObjectID read() throws IOException;

    void write(ObjectID id) throws IOException;

}
