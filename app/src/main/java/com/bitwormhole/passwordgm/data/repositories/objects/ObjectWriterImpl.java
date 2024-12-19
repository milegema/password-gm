package com.bitwormhole.passwordgm.data.repositories.objects;


import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.repositories.RepositoryContext;
import com.bitwormhole.passwordgm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.crypto.SecretKey;

final class ObjectWriterImpl implements ObjectWriter {

    private final RepositoryContext mContext;

    public ObjectWriterImpl(RepositoryContext ctx) {
        this.mContext = ctx;
    }


    @Override
    public ObjectEntity write(ObjectEntity entity) throws IOException {
        Path tmp_file = create_new_tmp_file();
        try {
            return this.inner_write(entity, tmp_file);
        } finally {
            this.clean_tmp_file(tmp_file);
        }
    }

    private Path create_new_tmp_file() throws IOException {
        String prefix = "obj-";
        String suffix = "-w.tmp~";
        Path base = this.mContext.getLayout().getObjects();
        Path dir = base.resolve("tmp");
        FileUtils.mkdirs(dir);
        return Files.createTempFile(dir, prefix, suffix);
    }

    private void clean_tmp_file(Path tmp) throws IOException {
        if (Files.isRegularFile(tmp)) {
            Files.delete(tmp);
        }
    }

    private ObjectEntity inner_write(ObjectEntity entity, Path tmp_file) throws IOException {

        ObjectEntity result = new ObjectEntity(entity);
        DataAccessRequest req = new DataAccessRequest();
        DataAccessStack stack = DataAccessStackFactory.getStack(DataAccessStackFactory.CONFIG.MAIN_DATA_CONTAINER);
        DataAccessBlock block = new DataAccessBlock();
        SecretKey sk = this.mContext.getSecretKeyManager().fetch();

        // prepare data

        block.setPlain(entity.getType(), entity.getContent());

        req.setStack(stack);
        req.setDam(DataAccessMode.REWRITE);
        req.setFile(tmp_file);
        req.setKeyPair(null);
        req.setSecretKey(sk);
        req.setBlocks(new DataAccessBlock[]{block});

        // write
        stack.getWriter().write(req);

        // move
        BlockID block_id = block.getPlain().getId();
        ObjectID oid = ObjectID.convert(block_id);
        Path target = SparseObjectFileLocator.locate(mContext, oid);
        FileUtils.mkdirsForFile(target);
        Files.move(tmp_file, target);

        // handle result
        result.setId(oid);
        return result;
    }
}
