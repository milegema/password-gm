package com.bitwormhole.passwordgm.encoding.blocks;

import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.HashUtils;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PlainBlock {

    private PropertyTable meta;
    private BlockID id;
    private ByteSlice encoded; // encoded = (type +'sp'+ length +'\0'+ content)


    private BlockType type;
    private ByteSlice content;


    public PlainBlock() {
    }

    public PlainBlock(PlainBlock src) {
        if (src == null) {
            return;
        }
        this.id = src.id;
        this.meta = src.meta;
        this.type = src.type;
        this.encoded = src.encoded;
        this.content = src.content;
    }

    public PlainBlock encode() {
        try {
            return PlainBlockCodec.encode(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PlainBlock decode() {
        return PlainBlockCodec.decode(this, null);
    }

    public PlainBlock decode(PlainBlockOptions opt) {
        return PlainBlockCodec.decode(this, opt);
    }


    public final boolean check() {
        try {
            final int len = content.getLength();
            final String head = String.valueOf(this.type) + ' ' + len + '\0';
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            buffer.write(head.getBytes(StandardCharsets.UTF_8));
            buffer.write(content.getData(), content.getOffset(), content.getLength());
            final byte[] sum = HashUtils.sum(buffer.toByteArray(), HashUtils.SHA256);
            final BlockID have = new BlockID(sum);
            final BlockID want = this.id;
            return have.equals(want);
        } catch (Exception e) {
            Logs.warn(e.getMessage());
            return false;
        }
    }

    public final void verify() {
        if (!this.check()) {
            throw new PGMException("bad sum of PlainBlock");
        }
    }


    public PropertyTable getMeta() {
        return meta;
    }

    public void setMeta(PropertyTable meta) {
        this.meta = meta;
    }

    public BlockID getId() {
        return id;
    }

    public void setId(BlockID id) {
        this.id = id;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public ByteSlice getEncoded() {
        return encoded;
    }

    public void setEncoded(ByteSlice encoded) {
        this.encoded = encoded;
    }

    public ByteSlice getContent() {
        return content;
    }

    public void setContent(ByteSlice content) {
        this.content = content;
    }
}
