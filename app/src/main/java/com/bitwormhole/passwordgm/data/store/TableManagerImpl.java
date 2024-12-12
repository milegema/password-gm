package com.bitwormhole.passwordgm.data.store;

public class TableManagerImpl implements TableManager {

    private TableContext context;

    public TableManagerImpl() {
    }

    @Override
    public TableHolder getTable(TableSelector sel) {
        TableFileLocation location = TableFileLocator.locate(context, sel);
        return TableHolderFactory.createTableHolder(context, location);
    }

    public TableContext getContext() {
        return context;
    }

    public void setContext(TableContext context) {
        this.context = context;
    }
}
