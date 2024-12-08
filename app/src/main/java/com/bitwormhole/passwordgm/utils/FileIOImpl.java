package com.bitwormhole.passwordgm.utils;

import java.io.IOException;
import java.nio.file.Path;

class FileIOImpl implements FileIO {

    public FileIOImpl() {
    }


    @Override
    public byte[] readBinary(Path file) throws IOException {
        throw new RuntimeException("no impl");
        //        return new byte[0];
    }

    @Override
    public String readText(Path file) throws IOException {
        throw new RuntimeException("no impl");
        //      return "";
    }

    @Override
    public void writeBinary(byte[] data, Path file, int flags) throws IOException {

        throw new RuntimeException("no impl");
    }

    @Override
    public void writeBinary(byte[] data, int offset, int length, Path file, int flags) throws IOException {

        throw new RuntimeException("no impl");
    }

    @Override
    public void writeText(String text, Path file, int flags) throws IOException {

        throw new RuntimeException("no impl");
    }
}
