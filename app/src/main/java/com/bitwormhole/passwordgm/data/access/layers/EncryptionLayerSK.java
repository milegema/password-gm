package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.blocks.CryptoBlock;
import com.bitwormhole.passwordgm.encoding.blocks.PlainBlock;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.CipherUtils;
import com.bitwormhole.passwordgm.security.Encryption;
import com.bitwormhole.passwordgm.security.PaddingMode;
import com.bitwormhole.passwordgm.security.SecurityRandom;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;

import javax.crypto.SecretKey;

/**
 * EncryptionLayer with SecretKey
 */
public class EncryptionLayerSK implements DataAccessLayer {


    public EncryptionLayerSK() {
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
    }


    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        DataAccessBlock[] blocks = request.getBlocks();
        for (DataAccessBlock block : blocks) {
            this.decryptBlock(block, request);
        }

        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        DataAccessBlock[] blocks = request.getBlocks();
        for (DataAccessBlock block : blocks) {
            this.encryptBlock(block, request);
        }

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }

    private void encryptBlock(DataAccessBlock block, DataAccessRequest request) {

        SecretKey key = request.getSecretKey();
        Encryption en1 = new Encryption();
        MyBlockHead head = new MyBlockHead();
        PlainBlock plain = block.getPlain();
        PropertyTable head_pt = plain.getMeta();

        if (head_pt == null) {
            head_pt = PropertyTable.Factory.create();
        }

        en1.setPlain(plain.getEncoded().toByteArray());
        en1.setIv(request.getIv());
        en1.setPadding(request.getPadding());
        en1.setMode(request.getMode());
        en1.setAlgorithm(key.getAlgorithm());

        this.prepareEncrypt(en1);

        head.algorithm = en1.getAlgorithm();
        head.mode = en1.getMode();
        head.padding = en1.getPadding();
        head.iv = en1.getIv();
        head.storeTo(head_pt);

        Encryption en2 = CipherUtils.encrypt(en1, key);

        CryptoBlock crypto = new CryptoBlock();
        crypto.setHead(head_pt);
        crypto.setBody(en2.getEncrypted());
        block.setEncrypted(crypto);
    }

    private void prepareEncrypt(Encryption en) {

        if (en.getAlgorithm() == null) {
            throw new PGMException(PGMErrorCode.Unknown, "algorithm is null");
        }

        if (en.getMode() == null) {
            en.setMode(CipherMode.CBC);
        }

        if (en.getPadding() == null) {
            en.setPadding(PaddingMode.PKCS7Padding);
        }

        // prepare IV
        boolean need_iv = CipherMode.requireIV(en.getMode());
        byte[] iv = en.getIv();
        if (need_iv) {
            if (iv == null) {
                iv = new byte[16];
                SecurityRandom.getRandom().nextBytes(iv);
            }
        } else {
            iv = null;
        }
        en.setIv(iv);
    }

    private void decryptBlock(DataAccessBlock block, DataAccessRequest request) {
        SecretKey key = request.getSecretKey();
        Encryption en1 = new Encryption();
        MyBlockHead head = new MyBlockHead();
        CryptoBlock crypto = block.getEncrypted();

        head.loadFrom(crypto.getHead());

        en1.setEncrypted(crypto.getBody());
        en1.setAlgorithm(head.algorithm);
        en1.setMode(head.mode);
        en1.setPadding(head.padding);
        en1.setIv(head.iv);

        Encryption en2 = CipherUtils.decrypt(en1, key);

        PlainBlock plain = new PlainBlock();
        plain.setMeta(crypto.getHead());
        plain.setEncoded(new ByteSlice(en2.getPlain()));
        block.setPlain(plain);
    }
}
