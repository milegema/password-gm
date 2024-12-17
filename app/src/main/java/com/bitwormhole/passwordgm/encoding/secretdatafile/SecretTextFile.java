package com.bitwormhole.passwordgm.encoding.secretdatafile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SecretTextFile extends SecretFileBase {

    public SecretTextFile() {
    }

    public String load() throws IOException {
        byte[] data = this.read();
        return new String(data, StandardCharsets.UTF_8);
    }

    public void save(String text) throws IOException {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        this.write(data);
    }
}
