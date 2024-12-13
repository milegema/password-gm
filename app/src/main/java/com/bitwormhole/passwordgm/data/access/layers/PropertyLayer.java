package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.utils.ByteSlice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PropertyLayer implements DataAccessLayer {


    public PropertyLayer() {

    }

    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        PropertyTable pt_r = PropertyTable.Factory.create();
        PropertyTable pt_w = PropertyTable.Factory.create();
        DataAccessBlock[] blocks = request.getBlocks();

        for (DataAccessBlock block : blocks) {
            byte[] plain = block.getPlain().getContent().toByteArray();
            String text = new String(plain, StandardCharsets.UTF_8);
            PropertyTable pt = PropertyTableLS.parse(text);
            pt_r.importAll(pt.exportAll(null));
            block.setText(text);
            block.setProperties(pt);
        }

        request.setPropertiesW(pt_w);
        request.setPropertiesR(pt_r);

        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        PropertyTable pt = request.getPropertiesW();
        String text = PropertyTableLS.stringify(pt);
        byte[] bin = text.getBytes(StandardCharsets.UTF_8);

        PlainBlock plain = new PlainBlock();
        plain.setContent(new ByteSlice(bin));
        plain.setType(BlockType.blob);

        DataAccessBlock block = new DataAccessBlock();
        block.setProperties(pt);
        block.setText(text);
        block.setPlain(plain);

        DataAccessBlock[] blocks = new DataAccessBlock[]{block};
        request.setBlocks(blocks);


        next.write(request);
        // Logs.debug(this + ".write() : end");
    }
}
