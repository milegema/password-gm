package com.bitwormhole.passwordgm.encoding.cryptfile;

import com.bitwormhole.passwordgm.encoding.pem.PEMBlock;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class CryptFileUtils {

    private static final String SECRET_FILE_HEAD = "CRYPT FILE HEAD";
    private static final String SECRET_FILE_BODY = "CRYPT FILE BODY";

    private CryptFileUtils() {
    }

    public static CryptFile convert(PEMDocument src) throws IOException {
        CryptFile dst = new CryptFile();
        PEMBlock[] blocks = src.getBlocks();
        if (blocks != null) {
            for (PEMBlock block : blocks) {
                String type = block.getType();
                if (SECRET_FILE_HEAD.equalsIgnoreCase(type)) {
                    dst.setHead(convertHead(block));
                } else if (SECRET_FILE_BODY.equalsIgnoreCase(type)) {
                    dst.setBody(block.getData());
                }
            }
        }
        return dst;
    }

    public static PEMDocument convert(CryptFile src) throws IOException {
        PEMDocument dst = new PEMDocument();
        PEMBlock b1 = convertHead(src.getHead());
        PEMBlock b2 = new PEMBlock();
        b2.setType(SECRET_FILE_BODY);
        b2.setData(src.getBody());
        dst.add(b1, b2);
        return dst;
    }

    private static PEMBlock convertHead(Properties src) throws IOException {
        PEMBlock dst = new PEMBlock();
        dst.setType(SECRET_FILE_HEAD);
        if (src == null) {
            dst.setData(new byte[0]);
            return dst;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        src.store(out, SECRET_FILE_HEAD);
        dst.setData(out.toByteArray());
        return dst;
    }

    private static Properties convertHead(PEMBlock src) throws IOException {
        Properties dst = new Properties();
        if (src == null) {
            return dst;
        }
        if (!SECRET_FILE_HEAD.equalsIgnoreCase(src.getType())) {
            return dst;
        }
        byte[] data = src.getData();
        if (data == null) {
            return dst;
        }

        String debugText = new String(data, StandardCharsets.UTF_8);
        //  in.reset();
        Logs.debug(debugText);


        ByteArrayInputStream in = new ByteArrayInputStream(data);
        dst.load(in);
        return dst;
    }
}
