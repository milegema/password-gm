package com.bitwormhole.passwordgm.encoding.secretdatafile;

import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.PaddingMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.crypto.SecretKey;

public class SecretDataFile {

    private Path file;
    private SecretKey key;
    private CipherMode mode;
    private PaddingMode padding;
    private byte[] iv;

    public SecretDataFile() {
    }

    /*


    public static class Head {
        public CipherMode mode;
        public PaddingMode padding;
        public String algorithm;
        public byte[] iv; // 在 props 中编码为 hex 格式

        public Head() {
        }

        final static String CIPHER_ALGORITHM = "algorithm";
        final static String CIPHER_MODE = "mode";
        final static String CIPHER_PADDING = "padding";
        final static String CIPHER_IV = "iv";
    }




    private static PropertyTable head2props(Head src) {
        PropertyTable dst = PropertyTable.Factory.create();
        PropertySetter setter = new PropertySetter(dst);
        setter.put(Head.CIPHER_ALGORITHM, src.algorithm);
        setter.put(Head.CIPHER_MODE, src.mode);
        setter.put(Head.CIPHER_PADDING, src.padding);
        setter.put(Head.CIPHER_IV, src.iv);
        return dst;
    }

    private static Head props2head(PropertyTable src) {
        Head dst = new Head();
        if (src == null) {
            return dst;
        }
        PropertyGetter getter = new PropertyGetter(src);
        getter.setRequired(false);
        dst.algorithm = getter.getString(Head.CIPHER_ALGORITHM, "AES");
        dst.padding = getter.getPaddingMode(Head.CIPHER_PADDING, PaddingMode.NoPadding);
        dst.mode = getter.getCipherMode(Head.CIPHER_MODE, CipherMode.ECB);
        dst.iv = getter.getHexData(Head.CIPHER_IV, null);
        return dst;
    }

    private static Head prepareHead(Head h1) {
        if (h1 == null) {
            h1 = new Head();
        }
        if (h1.mode == null) {
            h1.mode = CipherMode.ECB;
        }
        if (h1.padding == null) {
            h1.padding = PaddingMode.PKCS7Padding;
        }

        boolean need_iv = false;
        switch (h1.mode) {
            case CBC:
            case PCBC:
            case CFB:
            case OFB:
                need_iv = true;
                break;
        }

        if (need_iv) {
            if (h1.iv == null) {
                byte[] buffer = new byte[16];
                SecurityRandom.getRandom().nextBytes(buffer);
                h1.iv = buffer;
            }
        } else {
            h1.iv = null;
        }
        return h1;
    }



     */

    public byte[] read() throws IOException {

        /*

        PEMDocument pem_doc = PEMUtils.read(this.file);
        CryptFile crypt_file = CryptFileUtils.convert(pem_doc);
        Head head = props2head(crypt_file.getHead());
        this.mode = head.mode;
        this.padding = head.padding;
        this.iv = head.iv;

        Encryption en1 = new Encryption();
        en1.setIv(head.iv);
        en1.setMode(head.mode);
        en1.setAlgorithm(head.algorithm);
        en1.setPadding(head.padding);
        en1.setEncrypted(crypt_file.getBody());

        Encryption en2 = CipherUtils.decrypt(en1, this.key);
        return en2.getPlain();

         */

        throw new RuntimeException( "no impl" ) ;
    }

    public void write(byte[] data) throws IOException {

        /*

        // init head
        Head head = new Head();
        head.algorithm = this.key.getAlgorithm();
        head.mode = this.mode;
        head.padding = this.padding;
        head.iv = this.iv;

        // prepare head
        head = prepareHead(head);
        this.iv = head.iv;
        this.mode = head.mode;
        this.padding = head.padding;

        // encrypt
        Encryption en1 = new Encryption();
        en1.setIv(head.iv);
        en1.setMode(head.mode);
        en1.setAlgorithm(head.algorithm);
        en1.setPadding(head.padding);
        en1.setPlain(data);
        Encryption en2 = CipherUtils.encrypt(en1, this.key);

        // make crypt file
        CryptFile crypt_file = new CryptFile();
        crypt_file.setHead(head2props(head));
        crypt_file.setBody(en2.getEncrypted());

        // write to PEM file
        PEMDocument pem_doc = CryptFileUtils.convert(crypt_file);
        FileOptions opt = new FileOptions();
        opt.create = true;
        opt.write = true;
        opt.truncate = true;
        opt.mkdirs = true;
        PEMUtils.write(pem_doc, this.file, opt);

         */

        throw new RuntimeException( "no impl" ) ;

    }

    public void write(byte[] data, int offset, int length) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(data, offset, length);
        write(buffer.toByteArray());
    }


    public PaddingMode getPadding() {
        return padding;
    }

    public void setPadding(PaddingMode padding) {
        this.padding = padding;
    }

    public CipherMode getMode() {
        return mode;
    }

    public void setMode(CipherMode mode) {
        this.mode = mode;
    }


    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }
}
