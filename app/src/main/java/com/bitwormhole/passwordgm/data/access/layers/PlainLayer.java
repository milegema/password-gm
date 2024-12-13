package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;

import java.io.IOException;

public class PlainLayer implements DataAccessLayer {


    public PlainLayer() {

    }


    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        DataAccessBlock[] blocks = request.getBlocks();
        for (DataAccessBlock block : blocks) {
            this.decodeBlock(block);
        }


        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        DataAccessBlock[] blocks = request.getBlocks();
        for (DataAccessBlock block : blocks) {
            this.encodeBlock(block);
        }


        next.write(request);
        // Logs.debug(this + ".write() : end");
    }

    private void decodeBlock(DataAccessBlock block) {
        PlainBlock plain = block.getPlain();
        plain = plain.decode();
        block.setPlain(plain);
    }

    private void encodeBlock(DataAccessBlock block) {
        PlainBlock plain = block.getPlain();
        plain = plain.encode();
        block.setPlain(plain);
    }
}
