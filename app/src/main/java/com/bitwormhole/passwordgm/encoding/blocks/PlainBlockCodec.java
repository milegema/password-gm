package com.bitwormhole.passwordgm.encoding.blocks;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.HashUtils;
import com.bitwormhole.passwordgm.utils.PropertyGetter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PlainBlockCodec {

    private static final String HASH_ALGORITHM = HashUtils.SHA256;

    private static final String FIELD_ID = "id";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_CONTENT_LEN = "content-length";


    public static PlainBlock decode(PlainBlock src, PlainBlockOptions opt) {

        ByteSlice encoded = src.getEncoded();
        PropertyTable meta = src.getMeta();
        PropertyGetter meta_g = new PropertyGetter(meta);

        // get meta
        final byte[] id_bin = meta_g.getDataHex(FIELD_ID, null);
        final int len_want = meta_g.getInt(FIELD_CONTENT_LEN, 0);
        final BlockType type_want = meta_g.getBlockType(FIELD_TYPE, BlockType.BLOB);

        // decode
        PlainBlock tmp = innerDecodeContent(encoded);

        // check sum
        BlockID id_want = new BlockID(id_bin);
        if (isNeedCheckSum(opt)) {
            byte[] sum = HashUtils.sum(encoded, HASH_ALGORITHM);
            BlockID id_have = new BlockID(sum);
            if (!id_have.equals(id_want)) {
                throw new PGMException(PGMErrorCode.Unknown, "bad block id");
            }
        }

        // check type
        if (!type_want.equals(tmp.getType())) {
            throw new PGMException(PGMErrorCode.Unknown, "bad block type");
        }

        // check length
        final int len_have = tmp.getContent().getLength();
        if (len_have != len_want) {
            throw new PGMException(PGMErrorCode.Unknown, "bad block length");
        }

        PlainBlock dst = new PlainBlock(src);
        dst.setId(id_want);
        dst.setType(tmp.getType());
        dst.setContent(tmp.getContent());
        return dst;
    }

    private static PlainBlock innerDecodeContent(ByteSlice encoded) {

        final byte[] data = encoded.getData();
        final int off = encoded.getOffset();
        final int len = encoded.getLength();
        final int end = off + len;

        // seek for EOH
        int idx_end_of_head = 0;
        for (int i = off; i < end; i++) {
            final int b = data[i];
            if (b == 0) {
                idx_end_of_head = i;
                break;
            }
        }
        if (idx_end_of_head == 0) {
            throw new PGMException(PGMErrorCode.Unknown, "plain-block no EOH");
        }

        final int head_len = idx_end_of_head - off;
        final int body_len = end - idx_end_of_head - 1;
        final String head = new String(data, off, head_len, StandardCharsets.UTF_8);

        // parse head
        final int idx_space = head.indexOf(' ');
        final String type_str = head.substring(0, idx_space);
        final String len_str = head.substring(idx_space + 1);
        int len_int = Integer.parseInt(len_str);
        BlockType type = BlockType.valueOf(type_str);

        if (len_int != body_len) {
            throw new PGMException(PGMErrorCode.Unknown, "bad block content-length");
        }

        PlainBlock res = new PlainBlock();
        res.setContent(new ByteSlice(data, idx_end_of_head + 1, body_len));
        res.setType(type);
        return res;
    }

    private static boolean isNeedCheckSum(PlainBlockOptions opt) {
        if (opt == null) {
            return false;
        }
        return opt.checksum;
    }


    public static PlainBlock encode(PlainBlock src) throws IOException {

        ByteSlice content = src.getContent();
        PropertyTable meta = src.getMeta();

        // make head
        String type = String.valueOf(src.getType());
        String length = String.valueOf(content.getLength());
        String head_str = type + ' ' + length + '\0';
        byte[] head_bin = head_str.getBytes(StandardCharsets.UTF_8);

        // make encoded
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(head_bin);
        buffer.write(content.getData(), content.getOffset(), content.getLength());
        byte[] encoded = buffer.toByteArray();

        // sum
        byte[] sum = HashUtils.sum(encoded, HASH_ALGORITHM);
        BlockID id = new BlockID(sum);

        if (meta == null) {
            meta = PropertyTable.Factory.create();
        }
        meta.put(FIELD_ID, String.valueOf(id));
        meta.put(FIELD_TYPE, type);
        meta.put(FIELD_CONTENT_LEN, length);

        // result
        PlainBlock dst = new PlainBlock(src);
        dst.setEncoded(new ByteSlice(encoded));
        dst.setId(id);
        dst.setMeta(meta);
        return dst;
    }
}
