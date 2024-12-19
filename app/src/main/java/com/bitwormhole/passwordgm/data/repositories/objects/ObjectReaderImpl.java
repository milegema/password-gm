package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;

import java.io.IOException;

import javax.crypto.SecretKey;

final class ObjectReaderImpl implements ObjectReader {

    private final ObjectContext mContext;

    public ObjectReaderImpl(ObjectContext oc) {
        this.mContext = oc;
    }

    @Override
    public ObjectEntity read() throws IOException {

        DataAccessRequest req = new DataAccessRequest();
        DataAccessStack stack = DataAccessStackFactory.getStack(DataAccessStackFactory.CONFIG.MAIN_DATA_CONTAINER);
        SecretKey sk = mContext.getParent().getSecretKeyManager().fetch();
        ObjectEntity result = new ObjectEntity();
        ObjectID id_want = mContext.getId();

        // prepare
        req.setStack(stack);
        req.setFile(mContext.getFile());
        req.setSecretKey(sk);
        req.setDam(DataAccessMode.READONLY);
        req.setPadding(null);
        req.setMode(null);
        req.setIv(null);
        req.setBlocks(null);

        // read
        stack.getReader().read(req);

        // handle blocks
        DataAccessBlock[] blocks = req.getBlocks();
        for (DataAccessBlock block : blocks) {
            PlainBlock plain = block.getPlain();
            ObjectID id_have = ObjectID.convert(plain.getId());
            if (!id_have.equals(id_want)) {
                continue;
            }
            result.setId(id_have);
            result.setType(plain.getType());
            result.setContent(plain.getContent());
            return result;
        }
        throw new IOException("no object-block in the file, id:" + id_want);
    }
}
