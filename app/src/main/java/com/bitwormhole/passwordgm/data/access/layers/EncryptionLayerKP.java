package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.CipherUtils;
import com.bitwormhole.passwordgm.security.Encryption;
import com.bitwormhole.passwordgm.security.PaddingMode;
import com.bitwormhole.passwordgm.security.SecurityRandom;
import com.bitwormhole.passwordgm.utils.Logs;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;
import java.security.KeyPair;


/**
 * EncryptionLayer with KeyPair
 */
public class EncryptionLayerKP implements DataAccessLayer {


    public EncryptionLayerKP() {
    }

    private static class MyBlockHead {

        CipherMode mode;
        PaddingMode padding;
        String algorithm;
        byte[] iv;

        MyBlockHead() {
        }

        void loadFrom(PropertyTable pt) {
            PropertyGetter getter = new PropertyGetter(pt);
            getter.setRequired(false);
            this.algorithm = getter.getString(NAME_ALGORITHM, "AES");
            this.mode = getter.getCipherMode(NAME_MODE, CipherMode.CBC);
            this.padding = getter.getPaddingMode(NAME_PADDING, PaddingMode.PKCS7Padding);
            this.iv = getter.getData(NAME_IV, null);
        }

        void storeTo(PropertyTable pt) {
            PropertySetter setter = new PropertySetter(pt);
            setter.put(NAME_ALGORITHM, this.algorithm);
            setter.put(NAME_MODE, this.mode);
            setter.put(NAME_PADDING, this.padding);
            setter.put(NAME_IV, this.iv);
        }


        final static String NAME_ALGORITHM = "algorithm";
        final static String NAME_PADDING = "padding";
        final static String NAME_MODE = "mode";
        final static String NAME_IV = "iv";

        final static String NAME_INNER_CONTENT_TYPE = "inner.content-type";
        final static String NAME_INNER_ALGORITHM = "inner.algorithm";
    }


    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        DataAccessBlock[] blocks = request.getBlocks();
        for (DataAccessBlock block : blocks) {
            decryptBlock(block, request);
        }

        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        DataAccessBlock[] blocks = request.getBlocks();
        for (DataAccessBlock block : blocks) {
            encryptBlock(block, request);
        }

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }

    // private methods //////////////////////////

    private static void normalize(MyBlockHead head) {

        if (head.algorithm == null) {
            head.algorithm = "RSA";
        }
        if (head.mode == null) {
            head.mode = CipherMode.NONE;
        }
        if (head.padding == null) {
            head.padding = PaddingMode.PKCS1Padding;
        }

        if (head.iv == null) {
            if (CipherMode.requireIV(head.mode)) {
                byte[] buffer = new byte[16];
                SecurityRandom.getRandom().nextBytes(buffer);
                head.iv = buffer;
            }
        }
    }

    private static void decryptBlock(DataAccessBlock block, DataAccessRequest request) {

        KeyPair pair = request.getKeyPair();
        MyBlockHead head = new MyBlockHead();
        PropertyTable head_pt = block.getHead();
        Encryption en1 = new Encryption();

        head.loadFrom(head_pt);

        en1.setEncrypted(block.getBody());
        en1.setMode(head.mode);
        en1.setAlgorithm(head.algorithm);
        en1.setPadding(head.padding);
        en1.setIv(head.iv);

        Encryption en2 = CipherUtils.decrypt(en1, pair.getPrivate());

        block.setPlain(en2.getPlain());
    }

    private static void encryptBlock(DataAccessBlock block, DataAccessRequest request) {

        KeyPair pair = request.getKeyPair();
        MyBlockHead head = new MyBlockHead();
        PropertyTable head_pt = PropertyTable.Factory.create();
        Encryption en1 = new Encryption();

        head.algorithm = pair.getPublic().getAlgorithm();
        head.mode = request.getMode();
        head.padding = request.getPadding();
        head.iv = request.getIv();
        normalize(head);
        head.storeTo(head_pt);

        en1.setEncrypted(block.getBody());
        en1.setMode(head.mode);
        en1.setAlgorithm(head.algorithm);
        en1.setPadding(head.padding);
        en1.setIv(head.iv);
        en1.setPlain(block.getPlain());

        Encryption en2 = CipherUtils.encrypt(en1, pair.getPublic());

        block.setHead(head_pt);
        block.setBody(en2.getEncrypted());
    }
}
