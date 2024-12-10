package com.bitwormhole.passwordgm.encoding.secretkeyfile;

import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.encoding.cryptfile.CryptFile;
import com.bitwormhole.passwordgm.security.PaddingMode;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.nio.file.Path;
import java.security.KeyPair;
import java.util.Properties;

import javax.crypto.SecretKey;

public class SecretKeyFile {

    private CryptFile crypt;
    private Path file;
    private String alias;
    private SecretKey secretkey;
    private KeyPair keypair;
    private Head head;


    public SecretKeyFile() {
    }

    public SecretKeyFile(SecretKeyFile src) {
        if (src != null) {
            this.crypt = src.crypt;
            this.file = src.file;
            this.alias = src.alias;
            this.secretkey = src.secretkey;
            this.keypair = src.keypair;
            this.head = src.head;
        }
    }


    public static class Head {

        public String outerAlgorithm;
        public CipherMode outerMode;
        public PaddingMode outerPadding;

        public String innerAlgorithm;
        public String innerContentType;

        // names

        final static String INNER_ALGORITHM = "inner.algorithm";
        final static String INNER_CONTENT_TYPE = "inner.content-type";

        final static String OUTER_ALGORITHM = "outer.algorithm";
        final static String OUTER_MODE = "outer.mode";
        final static String OUTER_PADDING = "outer.padding";

    }


    public static Head props2head(Properties src) {
        Head dst = new Head();
        if (src == null) {
            return dst;
        }
        PropertyGetter getter = new PropertyGetter(src);
        getter.setRequired(false);

        dst.innerAlgorithm = getter.getString(Head.INNER_ALGORITHM, "NONE");
        dst.innerContentType = getter.getString(Head.INNER_CONTENT_TYPE, "application/octet-stream");

        dst.outerAlgorithm = getter.getString(Head.OUTER_ALGORITHM, "NONE");
        dst.outerMode = getter.getCipherMode(Head.OUTER_MODE, CipherMode.NONE);
        dst.outerPadding = getter.getPaddingMode(Head.OUTER_PADDING, PaddingMode.NoPadding);

        return dst;
    }

    public static Properties head2props(Head src) {
        Properties dst = new Properties();
        PropertySetter setter = new PropertySetter(dst);

        setter.put(Head.INNER_ALGORITHM, src.innerAlgorithm);
        setter.put(Head.INNER_CONTENT_TYPE, src.innerContentType);

        setter.put(Head.OUTER_ALGORITHM, src.outerAlgorithm);
        setter.put(Head.OUTER_MODE, src.outerMode);
        setter.put(Head.OUTER_PADDING, src.outerPadding);

        return dst;
    }


    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public KeyPair getKeypair() {
        return keypair;
    }

    public void setKeypair(KeyPair keypair) {
        this.keypair = keypair;
    }

    public SecretKey getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(SecretKey secretkey) {
        this.secretkey = secretkey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public CryptFile getCrypt() {
        return crypt;
    }

    public void setCrypt(CryptFile crypt) {
        this.crypt = crypt;
    }
}
