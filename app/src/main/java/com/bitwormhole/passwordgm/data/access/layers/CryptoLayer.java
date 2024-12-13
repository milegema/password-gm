package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.blocks.CryptoBlock;
import com.bitwormhole.passwordgm.encoding.blocks.CryptoFile;
import com.bitwormhole.passwordgm.encoding.blocks.CryptoFileUtils;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;

import java.io.IOException;

public class CryptoLayer implements DataAccessLayer {

    public CryptoLayer() {

    }

    private static DataAccessBlock convertForRead(CryptoBlock src) {
        DataAccessBlock dst = new DataAccessBlock();
        dst.setCrypto(src);
        return dst;
    }

    private static CryptoBlock convertForWrite(DataAccessBlock src) {
        return src.getCrypto();
    }

    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        // crypt <== pem
        CryptoFile cf = CryptoFileUtils.convert(request.getPem());
        CryptoBlock[] src = cf.getBlocks();
        DataAccessBlock[] dst = new DataAccessBlock[src.length];
        for (int i = 0; i < src.length; ++i) {
            dst[i] = convertForRead(src[i]);
        }
        request.setBlocks(dst);

        // Logs.debug(this + ".read() : end");
    }


    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {

        // Logs.debug(this + ".write() : begin");

        // crypt ==> pem
        DataAccessBlock[] src = request.getBlocks();
        CryptoBlock[] dst = new CryptoBlock[src.length];
        for (int i = 0; i < src.length; ++i) {
            dst[i] = convertForWrite(src[i]);
        }

        CryptoFile cf = new CryptoFile();
        cf.setBlocks(dst);
        PEMDocument pem = CryptoFileUtils.convert(cf);
        request.setPem(pem);

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }
}
