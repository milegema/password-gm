package com.bitwormhole.passwordgm.encoding.secretdatafile;

import java.io.IOException;

public class SecretBinaryFile extends SecretFileBase {

    public SecretBinaryFile() {
    }

    public byte[] load() throws IOException {
        return this.read();
    }

    public void save(byte[] data) throws IOException {
        this.write(data);
    }
}
