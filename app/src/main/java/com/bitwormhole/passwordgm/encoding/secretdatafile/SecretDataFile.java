package com.bitwormhole.passwordgm.encoding.secretdatafile;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.crypto.SecretKey;

public class SecretDataFile {

    private Path file;
    private SecretKey key;
    private CipherMode mode;
    private PaddingMode padding;
    private byte[] iv;
    private DataAccessMode dam;
    private BlockType type;

    public SecretDataFile() {
    }


    public byte[] read() throws IOException {

        DataAccessRequest req = new DataAccessRequest();
        DataAccessStack stack = DataAccessStackFactory.getStack(DataAccessStackFactory.CONFIG.MAIN_DATA_CONTAINER);
        // DataAccessBlock block = new DataAccessBlock();

        req.setStack(stack);
        req.setFile(this.file);
        req.setSecretKey(this.key);
        req.setDam(this.dam);
        req.setPadding(this.padding);
        req.setMode(this.mode);
        req.setIv(this.iv);
        req.setBlocks(null);

        stack.getReader().read(req);

        DataAccessBlock[] blocks = req.getBlocks();
        for (DataAccessBlock block : blocks) {
            this.setType(block.getPlainType());
            return block.getPlainContent();
        }

        throw new IOException("no PEM block in the file");
    }

    public void write(byte[] data) throws IOException {

        DataAccessRequest req = new DataAccessRequest();
        DataAccessStack stack = DataAccessStackFactory.getStack(DataAccessStackFactory.CONFIG.MAIN_DATA_CONTAINER);
        DataAccessBlock block = new DataAccessBlock();

        block.setPlain(this.type, data);

        req.setDam(this.dam);
        req.setFile(this.file);
        req.setKeyPair(null);
        req.setSecretKey(this.key);
        req.setBlocks(new DataAccessBlock[]{block});

        stack.getWriter().write(req);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(data, offset, length);
        write(buffer.toByteArray());
    }


    public PaddingMode getPadding() {
        return padding;
    }

    public void setPadding(PaddingMode padding) {
        this.padding = padding;
    }

    public CipherMode getMode() {
        return mode;
    }

    public void setMode(CipherMode mode) {
        this.mode = mode;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public DataAccessMode getDam() {
        return dam;
    }

    public void setDam(DataAccessMode dam) {
        this.dam = dam;
    }
}
