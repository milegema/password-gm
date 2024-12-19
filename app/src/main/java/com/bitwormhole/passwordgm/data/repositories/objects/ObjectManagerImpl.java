package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.repositories.RepositoryContext;

import java.nio.file.Path;

public class ObjectManagerImpl implements ObjectManager {

    private final RepositoryContext mContext;

    public ObjectManagerImpl(RepositoryContext ctx) {
        this.mContext = ctx;
    }

    @Override
    public ObjectHolder get(ObjectID id) {
        Path file = SparseObjectFileLocator.locate(mContext, id);
        ObjectContext oc = new ObjectContext();
        oc.setId(id);
        oc.setParent(this.mContext);
        oc.setFile(file);
        oc.setHolder(new ObjectHolderImpl(oc));
        oc.setReader(new ObjectReaderImpl(oc));
        return oc.getHolder();
    }

    @Override
    public ObjectWriter writer() {
        return new ObjectWriterImpl(this.mContext);
    }
}
