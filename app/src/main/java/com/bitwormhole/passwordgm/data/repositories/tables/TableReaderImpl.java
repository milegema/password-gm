package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

final class TableReaderImpl implements TableReader {

    private final static int REWRITE_THRESHOLD = 32;

    private final TableContext mContext;

    public TableReaderImpl(TableContext ctx) {
        this.mContext = ctx;
    }

    private class MyReadTask implements Runnable {


        public PropertyTable result;
        Exception error;

        @Override
        public void run() {
            try {
                PropertyTable cache = mContext.getCache();
                if (cache == null) {
                    cache = this.loadFromFile();
                    mContext.setCache(cache);
                }
                this.result = cache;
            } catch (Exception e) {
                this.error = e;
            }
        }

        private PropertyTable loadFromFile() throws IOException {

            PropertyTable src;
            PropertyTable dst = PropertyTable.Factory.create();
            Map<String, String> tmp = new HashMap<>();
            DataAccessRequest req = new DataAccessRequest();
            DataAccessStack stack = DataAccessStackFactory.getStack(DataAccessStackFactory.CONFIG.MAIN_DATA_CONTAINER);

            req.setStack(stack);
            req.setFile(mContext.getFile());
            req.setSecretKey(mContext.getKey());
            req.setDam(DataAccessMode.READONLY);
            req.setPadding(null);
            req.setMode(null);
            req.setIv(null);
            req.setBlocks(null);

            // read
            stack.getReader().read(req);

            // parse blocks
            DataAccessBlock[] block_list = req.getBlocks();
            if (block_list.length > REWRITE_THRESHOLD) {
                mContext.setNeedRewrite(true);
            }
            for (DataAccessBlock block : block_list) {
                src = this.parseBlock(block);
                if (src == null) {
                    continue;
                }
                tmp = src.exportAll(tmp);
            }
            dst.importAll(tmp);
            return dst;
        }

        private PropertyTable parseBlock(DataAccessBlock block) {
            BlockType type = block.getPlainType();
            if (BlockType.Properties.equals(type) || BlockType.Table.equals(type) || BlockType.BLOB.equals(type)) {
                byte[] data = block.getPlainContent();
                return PropertyTableLS.decode(data);
            }
            return null;
        }

        PropertyTable getResult() throws IOException {
            if (this.error == null) {
                if (this.result == null) {
                    throw new IOException("table cache is null");
                }
                return this.result;
            }
            throw new IOException(this.error);
        }
    }


    @Override
    public PropertyTable read() throws IOException {
        PropertyTable cache = mContext.getCache();
        if (cache != null) {
            return cache;
        }
        MyReadTask task = new MyReadTask();
        mContext.getSynchronizedWorker().execute(task);
        return task.getResult();
    }
}
