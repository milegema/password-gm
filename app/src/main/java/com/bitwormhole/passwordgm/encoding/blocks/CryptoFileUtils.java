package com.bitwormhole.passwordgm.encoding.blocks;

import com.bitwormhole.passwordgm.encoding.pem.PEMBlock;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CryptoFileUtils {

    private static final String CRYPT_BLOCK = "CRYPT BLOCK";

    private CryptoFileUtils() {
    }

    public static CryptoFile convert(PEMDocument src_doc) throws IOException {
        CryptoFile dst_file = new CryptoFile();
        List<CryptoBlock> dst = new ArrayList<>();
        PEMBlock[] src = src_doc.getBlocks();
        if (src != null) {
            for (PEMBlock block1 : src) {
                CryptoBlock block2 = parseBlock(block1);
                if (block2 == null) {
                    continue;
                }
                dst.add(block2);
            }
        }
        dst_file.setBlocks(dst.toArray(new CryptoBlock[0]));
        return dst_file;
    }

    public static PEMDocument convert(CryptoFile src_file) throws IOException {
        PEMDocument dst_doc = new PEMDocument();
        List<PEMBlock> dst = new ArrayList<>();
        CryptoBlock[] src = src_file.getBlocks();
        if (src != null) {
            for (CryptoBlock block1 : src) {
                PEMBlock block2 = makeBlock(block1);
                if (block2 == null) {
                    continue;
                }
                dst.add(block2);
            }
        }
        dst_doc.setBlocks(dst.toArray(new PEMBlock[0]));
        return dst_doc;
    }

    /////////// private

    private static PEMBlock makeBlock(CryptoBlock src) throws IOException {
        PEMBlock dst = new PEMBlock();
        dst.setType(CRYPT_BLOCK);
        if (src == null) {
            return null;
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

    private static CryptoBlock parseBlock(PEMBlock src) throws IOException {
        if (src == null) {
            return null;
        }
        if (!CRYPT_BLOCK.equalsIgnoreCase(src.getType())) {
            return null;
        }
        byte[] data = src.getData();
        if (data == null) {
            return null;
        }
        final CryptoBlock dst = new CryptoBlock();
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
