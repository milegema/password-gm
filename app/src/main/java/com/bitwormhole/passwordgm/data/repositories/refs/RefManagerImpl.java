package com.bitwormhole.passwordgm.data.repositories.refs;

import com.bitwormhole.passwordgm.data.repositories.RepositoryContext;
import com.bitwormhole.passwordgm.errors.PGMException;

import java.nio.file.Path;

public class RefManagerImpl implements RefManager {

    private final RepositoryContext mContext;

    public RefManagerImpl(RepositoryContext ctx) {
        this.mContext = ctx;
    }

    private Path locate(RefName name) {
        //check name
        final String prefix = "refs";
        String str = name.toString();
        if (!str.startsWith(prefix)) {
            throw new PGMException("bad ref name: " + name);
        }
        // resolve
        Path base = mContext.getLayout().getRefs().getParent();
        return base.resolve(str);
    }


    @Override
    public RefHolder get(RefName name) {
        RefContext ctx = new RefContext();
        ctx.setPath(this.locate(name));
        ctx.setName(name);
        ctx.setKey(this.mContext.getSecretKey());
        RefFactory.create(ctx);
        return ctx.getHolder();
    }
}
