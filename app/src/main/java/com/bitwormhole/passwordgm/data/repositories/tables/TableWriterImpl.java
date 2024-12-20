package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretPropertyFile;

import java.io.IOException;
import java.util.Map;

final class TableWriterImpl implements TableWriter {


    private final TableContext mContext;

    public TableWriterImpl(TableContext ctx) {
        this.mContext = ctx;
    }

    private class MyWriteTask {

        private final PropertyTable src;
        Exception error;

        MyWriteTask() {
            this.src = mContext.getBuffer();
        }

        public void doAppend() throws IOException {
            SecretPropertyFile file = new SecretPropertyFile();
            file.setFile(mContext.getFile());
            file.setDam(DataAccessMode.APPEND);
            file.setKey(mContext.getKey());
            file.setType(BlockType.Table);
            file.save(this.src);
        }

        public void doRewrite() throws IOException {

            // prepare
            SecretPropertyFile file = new SecretPropertyFile();
            file.setFile(mContext.getFile());
            file.setKey(mContext.getKey());
            file.setDam(DataAccessMode.READONLY);

            // mix sources to dst
            PropertyTable src1 = file.load();
            PropertyTable src2 = mContext.getCache();
            PropertyTable[] sources = new PropertyTable[]{src1, src2, this.src};
            PropertyTable dst = PropertyTable.Factory.create();
            for (PropertyTable s : sources) {
                if (s == null) {
                    continue;
                }
                dst.importAll(s.exportAll(null));
            }

            // write to file
            file.setType(BlockType.Table);
            file.setDam(DataAccessMode.REWRITE);
            file.save(dst);
        }
    }

    @Override
    public void write(PropertyTable src) throws IOException {
        if (src == null) {
            return;
        }
        PropertyTable buffer = mContext.getBuffer();
        PropertyTable cache = mContext.getCache();
        Map<String, String> tmp = src.exportAll(null);
        if (cache != null) {
            cache.importAll(tmp);
        }
        if (buffer == null) {
            buffer = PropertyTable.Factory.create();
            mContext.setBuffer(buffer);
        }
        buffer.importAll(tmp);
    }

    @Override
    public void flush() throws IOException {
        final boolean need_rewrite = mContext.isNeedRewrite();
        final MyWriteTask task = new MyWriteTask();
        final long now = System.currentTimeMillis();
        PropertyTable buffer = mContext.getBuffer();
        if (buffer != null) {
            buffer.put("table.updated_at", String.valueOf(now));
        }
        mContext.setNeedRewrite(false);
        mContext.getSynchronizedWorker().execute(() -> {
            if (task.src == null) {
                return;
            }
            try {
                if (need_rewrite) {
                    task.doRewrite();
                } else {
                    task.doAppend();
                }
            } catch (Exception e) {
                //     throw new RuntimeException(e);
                task.error = e;
            }
        });
        if (task.error == null) {
            return;
        }
        throw new IOException(task.error);
    }
}
