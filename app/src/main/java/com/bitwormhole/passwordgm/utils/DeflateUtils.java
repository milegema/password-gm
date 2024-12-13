package com.bitwormhole.passwordgm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public final class DeflateUtils {


    private DeflateUtils() {
    }


    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeflaterOutputStream f = new DeflaterOutputStream(out);
        f.write(data);
        f.flush();
        f.close();
        return out.toByteArray();
    }

    public static byte[] expand(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InflaterOutputStream f = new InflaterOutputStream(out);
        f.write(data);
        f.flush();
        f.close();
        return out.toByteArray();
    }
}
