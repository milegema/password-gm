package com.bitwormhole.passwordgm.security;

import java.io.IOException;
import java.nio.file.Path;

public interface SecretFileIO {

    byte[] readBinary(Path file) throws IOException;

    String readText(Path file) throws IOException;

    void writeBinary(byte[] data, Path file, int flags) throws IOException;

    void writeBinary(byte[] data, int offset, int length, Path file, int flags) throws IOException;

    void writeText(String text, Path file, int flags) throws IOException;

}
