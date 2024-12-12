package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.cryptfile.CryptBlock;
import com.bitwormhole.passwordgm.encoding.cryptfile.CryptFile;
import com.bitwormhole.passwordgm.encoding.cryptfile.CryptFileUtils;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;

public class CryptoLayer implements DataAccessLayer {

    public CryptoLayer() {

    }

    private static DataAccessBlock convertForRead(CryptBlock src) {
        DataAccessBlock dst = new DataAccessBlock();
        dst.setHead(src.getHead());
        dst.setBody(src.getBody());
        dst.setPlain(null);
        dst.setText(null);
        dst.setProperties(null);
        return dst;
    }

    private static CryptBlock convertForWrite(DataAccessBlock src) {
        return src;
    }

    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        // crypt <== pem
        CryptFile cf = CryptFileUtils.convert(request.getPem());
        CryptBlock[] src = cf.getBlocks();
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
        CryptBlock[] dst = new CryptBlock[src.length];
        for (int i = 0; i < src.length; ++i) {
            dst[i] = convertForWrite(src[i]);
        }

        CryptFile cf = new CryptFile();
        cf.setBlocks(dst);
        PEMDocument pem = CryptFileUtils.convert(cf);
        request.setPem(pem);

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }
}
