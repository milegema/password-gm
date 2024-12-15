package com.bitwormhole.passwordgm.encoding.secretkeyfile;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.PropertyGetter;

import java.io.IOException;
import java.nio.file.Path;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class SecretKeyFileLS {

    public static SecretKeyFile load(SecretKeyFile src) throws IOException {

        SecretKeyFile dst = new SecretKeyFile(src);
        DataAccessStack st = getStack();
        DataAccessRequest req = new DataAccessRequest();
        Path file = src.getFile();

        req.setDam(DataAccessMode.READONLY);
        req.setFile(file);
        req.setKeyPair(src.getOuter());
        req.setStack(st);

        st.getReader().read(req);

        // parse secret-key
        DataAccessBlock[] blocks = req.getBlocks();
        for (DataAccessBlock block : blocks) {
            SecretKey sk = tryParseSecretKey(block);
            if (sk != null) {
                dst.setInner(sk);
                return dst;
            }
        }
        throw new PGMException(PGMErrorCode.Unknown, "cannot find xf in file " + file);
    }

    public static void store(SecretKeyFile target) throws IOException {

        SecretKey sk = target.getInner();
        byte[] data = sk.getEncoded();
        PropertyTable meta = PropertyTable.Factory.create();
        meta.put("key.secret.algorithm", sk.getAlgorithm());
        meta.put("key.secret.format", sk.getFormat());

        PlainBlock pb = new PlainBlock();
        pb.setType(BlockType.SecretKey);
        pb.setContent(new ByteSlice(data));
        pb.setMeta(meta);

        DataAccessBlock block = new DataAccessBlock();
        block.setPlain(pb);

        DataAccessRequest req = new DataAccessRequest();
        req.setFile(target.getFile());
        req.setKeyPair(target.getOuter());
        req.setSecretKey(target.getInner());
        req.setDam(DataAccessMode.REWRITE);
        req.setBlocks(new DataAccessBlock[]{block});

        DataAccessStack st = getStack();
        st.getWriter().write(req);
    }

    // private

    private static DataAccessStack _stack;

    private SecretKeyFileLS() {
    }


    private static SecretKey tryParseSecretKey(DataAccessBlock block) {
        PlainBlock plain = block.getPlain();
        BlockType type = plain.getType();
        if (!BlockType.SecretKey.equals(type)) {
            return null;
        }
        PropertyGetter getter = new PropertyGetter(plain.getMeta());
        getter.setRequired(true);
        String algorithm = getter.getString("key.secret.algorithm", null);
        byte[] bin = plain.getContent().toByteArray();
        return new SecretKeySpec(bin, algorithm);
    }

    private static DataAccessStack getStack() {
        DataAccessStack st = _stack;
        if (st == null) {
            st = initStack();
            _stack = st;
        }
        return st;
    }

    private static DataAccessStack initStack() {
        return DataAccessStackFactory.getStack(DataAccessStackFactory.CONFIG.MAIN_KEY_CONTAINER);
    }
}
