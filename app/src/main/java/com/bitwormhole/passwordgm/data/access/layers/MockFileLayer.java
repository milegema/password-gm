package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MockFileLayer implements DataAccessLayer {

    private final Map<String, MyDataHolder> files;

    public MockFileLayer() {
        this.files = new HashMap<>();
    }

    private static class MyDataHolder {

        private byte[] data;
        private final String key;

        MyDataHolder(String _key) {
            this.key = _key;
        }

        public byte[] getData() {
            byte[] d = this.data;
            if (d == null) {
                return new byte[0];
            }
            return Arrays.copyOf(d, d.length);
        }

        public void setData(byte[] d) {
            if (d == null) {
                return;
            }
            this.data = Arrays.copyOf(d, d.length);
        }
    }


    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);
        this.doRead(request);
        // Logs.debug(this + ".read() : end");
    }


    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");
        this.doWrite(request);
        next.write(request);
        // Logs.debug(this + ".write() : end");
    }

    private static String keyOf(DataAccessRequest request) {
        Path file = request.getFile();
        if (file == null) {
            return "{null}";
        }
        return file.toString();
    }

    private void doRead(DataAccessRequest request) {
        final String key = keyOf(request);
        MyDataHolder h = this.files.get(key);
        if (h == null) {
            return;
        }
        request.setRaw(h.getData());
    }

    private void doWrite(DataAccessRequest request) {
        final String key = keyOf(request);
        final MyDataHolder h = new MyDataHolder(key);
        h.setData(request.getRaw());
        this.files.put(h.key, h);
    }
}
