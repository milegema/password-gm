package com.bitwormhole.passwordgm.encoding.secretdatafile;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;

import java.io.IOException;

public class SecretPropertyFile extends SecretFileBase {

    public SecretPropertyFile() {
    }

    public PropertyTable load() throws IOException {
        byte[] data = this.read();
        return PropertyTableLS.decode(data);
    }

    public void save(PropertyTable pt) throws IOException {
        byte[] data = PropertyTableLS.encode(pt);
        this.write(data);
    }
}
