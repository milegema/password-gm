package com.bitwormhole.passwordgm.encoding.secretdatafile;

import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.crypto.SecretKey;

public final class SecretDataFileLS {


    private SecretDataFileLS() {
    }

    private static DataAccessStack _stack;

    private static DataAccessStack getStack() {
        DataAccessStack st = _stack;
        if (st == null) {
            st = initStack();
            _stack = st;
        }
        return st;
    }

    private static DataAccessStack initStack() {
        return DataAccessStackFactory.createStack(DataAccessStackFactory.CONFIG.MAIN_DATA_CONTAINER);
    }


    public static void load(SecretDataFile target) throws IOException {
        DataAccessStack st = getStack();
        DataAccessRequest req = new DataAccessRequest();

        req.setFile(target.getFile());
        req.setSecretKey(target.getKey());

        st.getReader().read(req);
        // target.set
    }

    public static void save(SecretDataFile target) throws IOException {

        DataAccessStack st = getStack();
        DataAccessRequest req = new DataAccessRequest();

        req.setFile(target.getFile());
        req.setSecretKey(target.getKey());

        st.getWriter().write(req);
    }
}
