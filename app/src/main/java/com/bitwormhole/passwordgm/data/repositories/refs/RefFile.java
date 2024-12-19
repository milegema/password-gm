package com.bitwormhole.passwordgm.data.repositories.refs;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.ObjectID;

import java.io.IOException;

public interface RefFile extends Ref {

    ObjectID read() throws IOException;

    void write(ObjectID id) throws IOException;

}
