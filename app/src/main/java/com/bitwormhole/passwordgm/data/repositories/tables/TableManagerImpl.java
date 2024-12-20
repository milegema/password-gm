package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.data.repositories.RepositoryContext;

import java.nio.file.Path;
import java.util.concurrent.Executor;

public class TableManagerImpl implements TableManager {

    private final RepositoryContext mContext;

    public TableManagerImpl(RepositoryContext ctx) {
        this.mContext = ctx;
    }


    private static class MySyncWorker implements Executor {
        @Override
        public synchronized void execute(Runnable command) {
            if (command == null) {
                return;
            }
            command.run();
        }
    }

    private static final MySyncWorker _sync_worker = new MySyncWorker();


    @Override
    public TableHolder get(TableName name) {
        TableContext ctx = new TableContext();

        ctx.setFile(this.getTableFile(name));
        ctx.setName(name);
        ctx.setReader(new TableReaderImpl(ctx));
        ctx.setWriter(new TableWriterImpl(ctx));
        ctx.setHolder(new TableHolderImpl(ctx));
        ctx.setSynchronizedWorker(_sync_worker);
        ctx.setKey(mContext.getSecretKey());

        return ctx.getHolder();
    }

    private Path getTableFile(TableName name) {
        Path tables_dir = this.mContext.getLayout().getTables();
        return tables_dir.resolve(name + "/table.data");
    }
}
