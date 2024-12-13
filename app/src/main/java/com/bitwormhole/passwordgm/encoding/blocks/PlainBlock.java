package com.bitwormhole.passwordgm.encoding.blocks;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.ByteSlice;

import java.io.IOException;

public class PlainBlock {

    private PropertyTable meta;
    private BlockID id;
    private BlockType type;
    private ByteSlice encoded; // encoded = (type +'sp'+ length +'\0'+ content)
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
