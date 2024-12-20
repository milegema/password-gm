package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretPropertyFile;
import com.bitwormhole.passwordgm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executor;

final class TableHolderImpl implements TableHolder {

    private final TableContext mContext;

    public TableHolderImpl(TableContext ctx) {
        this.mContext = ctx;
    }

    @Override
    public TableName name() {
        return mContext.getName();
    }

    @Override
    public boolean create() {
        Executor worker = this.mContext.getSynchronizedWorker();
        if (worker == null) {
            try {
                return this.innerCreate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        worker.execute(() -> {
            try {
                innerCreate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

    private boolean innerCreate() throws IOException {

        Path target = mContext.getFile();
        TableName name = mContext.getName();
        PropertyTable meta = PropertyTable.Factory.create();
        long now = System.currentTimeMillis();

        meta.put("table.name", "" + name);
        meta.put("table.location", "" + target);
        meta.put("table.created_at", "" + now);


        FileUtils.mkdirsForFile(target);
        Files.write(target, new byte[0], StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);


        SecretPropertyFile file = new SecretPropertyFile();
        file.setFile(target);
        file.setDam(DataAccessMode.REWRITE);
        file.setKey(mContext.getKey());

        file.save(meta);

        return false;
    }


    @Override
    public boolean exists() {
        Path file = mContext.getFile();
        return Files.exists(file);
    }

    @Override
    public TableReader reader() throws IOException {
        return mContext.getReader();
    }

    @Override
    public TableWriter writer() throws IOException {
        return mContext.getWriter();
    }
}
