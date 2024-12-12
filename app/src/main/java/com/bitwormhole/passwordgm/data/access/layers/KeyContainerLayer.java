package com.bitwormhole.passwordgm.data.access.layers;

import com.bitwormhole.passwordgm.data.access.DataAccessLayer;
import com.bitwormhole.passwordgm.data.access.DataAccessReaderChain;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessWriterChain;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.utils.PropertyGetter;
import com.bitwormhole.passwordgm.utils.PropertySetter;

import java.io.IOException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 为保存 SecretKey 提供支持
 */
public class KeyContainerLayer implements DataAccessLayer {


    public KeyContainerLayer() {

    }

    private static class MySecretKeyInfo {
        String algorithm;
        String format;
        byte[] encoded;

        MySecretKeyInfo() {
        }

        final static String FIELD_ALGORITHM = "algorithm";
        final static String FIELD_FORMAT = "format";
        final static String FIELD_ENCODED = "encoded";
    }

    @Override
    public void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException {
        // Logs.debug(this + ".read() : begin");
        next.read(request);

        MySecretKeyInfo info = new MySecretKeyInfo();
        PropertyTable pt = request.getPropertiesR();

        convert(pt, info);
        SecretKey sk = convert2sk(info);

        request.setSecretKey(sk);

        // Logs.debug(this + ".read() : end");
    }

    @Override
    public void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException {
        // Logs.debug(this + ".write() : begin");

        SecretKey sk = request.getSecretKey();
        MySecretKeyInfo info = new MySecretKeyInfo();
        PropertyTable pt = PropertyTable.Factory.create();

        convert(sk, info);
        convert(info, pt);

        request.setOverwriteWholeFile(true);
        request.setPropertiesW(pt);

        next.write(request);
        // Logs.debug(this + ".write() : end");
    }

    private void convert(SecretKey src, MySecretKeyInfo dst) {
        dst.algorithm = src.getAlgorithm();
        dst.encoded = src.getEncoded();
        dst.format = src.getFormat();
    }

    private SecretKey convert2sk(MySecretKeyInfo src) {
        return new SecretKeySpec(src.encoded, src.algorithm);
    }

    private void convert(PropertyTable src, MySecretKeyInfo dst) {
        PropertyGetter g = new PropertyGetter(src);
        g.setRequired(true);
        dst.algorithm = g.getString(MySecretKeyInfo.FIELD_ALGORITHM, null);
        dst.format = g.getString(MySecretKeyInfo.FIELD_FORMAT, null);
        dst.encoded = g.getData(MySecretKeyInfo.FIELD_ENCODED, null);
    }

    private void convert(MySecretKeyInfo src, PropertyTable dst) {
        PropertySetter s = new PropertySetter(dst);
        s.put(MySecretKeyInfo.FIELD_ALGORITHM, src.algorithm);
        s.put(MySecretKeyInfo.FIELD_FORMAT, src.format);
        s.put(MySecretKeyInfo.FIELD_ENCODED, src.encoded);
    }
}
