package com.bitwormhole.passwordgm.encoding.cryptfile;

import com.bitwormhole.passwordgm.encoding.pem.PEMBlock;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CryptFileUtils {

    private static final String SECRET_FILE = "CRYPT FILE";

    private CryptFileUtils() {
    }

    public static CryptFile convert(PEMDocument src) throws IOException {
        CryptFile dst = null;
        PEMBlock[] blocks = src.getBlocks();
        if (blocks != null) {
            for (PEMBlock block : blocks) {
                if (SECRET_FILE.equalsIgnoreCase(block.getType())) {
                    dst = parseBlock(block);
                }
            }
        }
        if (dst == null) {
            dst = new CryptFile();
        }
        return dst;
    }

    public static PEMDocument convert(CryptFile src) throws IOException {
        PEMDocument dst = new PEMDocument();
        PEMBlock block = makeBlock(src);
        dst.add(block);
        return dst;
    }

    /////////// private

    private static PEMBlock makeBlock(CryptFile src) throws IOException {
        PEMBlock dst = new PEMBlock();
        dst.setType(SECRET_FILE);
        if (src == null) {
            dst.setData(new byte[0]);
            return dst;
        }
        PropertyTable head = src.getHead();
        byte[] body = src.getBody();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (head != null) {
            byte[] bin = PropertyTableLS.encode(head);
            out.write(bin);
        }
        out.write(0);
        if (body != null) {
            out.write(body);
        }
        dst.setData(out.toByteArray());
        return dst;
    }

    private static CryptFile parseBlock(PEMBlock src) throws IOException {
        CryptFile dst = new CryptFile();
        if (src == null) {
            return dst;
        }
        if (!SECRET_FILE.equalsIgnoreCase(src.getType())) {
            return dst;
        }
        byte[] data = src.getData();
        if (data == null) {
            return dst;
        }
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final int total = data.length;
        int offset, length;
        offset = length = 0;
        for (int i = 0; i < total; i++) {
            int b = data[i];
            if (b == 0) {
                PropertyTable head = PropertyTableLS.decode(buffer.toByteArray());
                dst.setHead(head);
                offset = i + 1;
                length = total - offset;
                break;
            }
            buffer.write(b);
        }
        buffer.reset();
        buffer.write(data, offset, length);
        dst.setBody(buffer.toByteArray());
        return dst;
    }
}
