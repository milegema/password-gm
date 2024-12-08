package com.bitwormhole.passwordgm.security.pem;

import com.bitwormhole.passwordgm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

public final class PEMUtils {

    private PEMUtils() {
    }

    public static PEMDocument read(Path file) throws IOException {
        String text = FileUtils.readText(file);
        return decode(text);
    }

    public static void write(PEMDocument doc, Path dst) throws IOException {
        int flags = 0;
        String text = encode(doc);
        FileUtils.writeText(text, dst, flags);
    }

    public static String encode(PEMDocument doc) {
        return PEMEncoder.encode(doc);
    }

    public static PEMDocument decode(String text) {
        return PEMDecoder.decode(text);
    }
}
