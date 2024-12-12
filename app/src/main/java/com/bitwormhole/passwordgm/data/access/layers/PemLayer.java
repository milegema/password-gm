package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.pem.PEMDocument;
import com.bitwormhole.passwordgm.encoding.pem.PEMUtils;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PemLayer implements DataAccessLayer {

    public PemLayer() {
    }

    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        String str = new String(request.getRaw(), StandardCharsets.UTF_8);
        PEMDocument doc = PEMUtils.decode(str);
        request.setPem(doc);

        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        String str = PEMUtils.encode(request.getPem());
        if (!request.isOverwriteWholeFile()) {
            final String nl = "\n";
            str = nl + str + nl;
        }
        request.setRaw(str.getBytes(StandardCharsets.UTF_8));

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }
}
