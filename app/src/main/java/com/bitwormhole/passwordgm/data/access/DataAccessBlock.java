package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.blocks.BlockTypeSet;
import com.bitwormhole.passwordgm.encoding.blocks.CryptoBlock;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.ByteSlice;

public class DataAccessBlock {

    private CryptoBlock encrypted;
    private PlainBlock plain;


    public DataAccessBlock() {
    }


    public PlainBlock getPlain() {
        return plain;
    }

    public void setPlain(PlainBlock plain) {
        this.plain = plain;
    }

    public CryptoBlock getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(CryptoBlock encrypted) {
        this.encrypted = encrypted;
    }


    // extend methods

    public void setPlain(BlockType type, byte[] content) {
        type = BlockTypeSet.normalize(type, BlockType.BLOB);
        PlainBlock pb = new PlainBlock();
        pb.setContent(new ByteSlice(content));
        pb.setType(type);
        this.setPlain(pb);
    }

    public void setPlain(BlockType type, byte[] content, int offset, int length) {
        type = BlockTypeSet.normalize(type, BlockType.BLOB);
        PlainBlock pb = new PlainBlock();
        pb.setContent(new ByteSlice(content, offset, length));
        pb.setType(type);
        this.setPlain(pb);
    }

    public void setPlain(BlockType type, ByteSlice content) {
        type = BlockTypeSet.normalize(type, BlockType.BLOB);
        PlainBlock pb = new PlainBlock();
        pb.setContent(new ByteSlice(content));
        pb.setType(type);
        this.setPlain(pb);
    }

    public BlockType getPlainType() {
        PlainBlock pb = this.plain;
        if (pb != null) {
            BlockType type = pb.getType();
            if (type != null) {
                return type;
            }
        }
        return BlockType.BLOB;
    }

    public byte[] getPlainContent() {
        PlainBlock pb = this.plain;
        if (pb != null) {
            ByteSlice slice = pb.getContent();
            if (slice != null) {
                return slice.toByteArray();
            }
        }
        return new byte[0];
    }
}
