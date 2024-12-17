package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFile;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFileLS;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.security.CipherMode;
import com.bitwormhole.passwordgm.security.Encryption;
import com.bitwormhole.passwordgm.security.PaddingMode;
import com.bitwormhole.passwordgm.security.SecurityRandom;
import com.bitwormhole.passwordgm.utils.Hex;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@RunWith(AndroidJUnit4.class)
public class KeyContainerTest {

    @Test
    public void useKeyContainerStack() throws IOException, NoSuchAlgorithmException {

        SecretKey s_key = prepareSecretKey();
        KeyPair keypair = prepareKeyPair();
        List<Encryption> mode_list = listExampleMode();
        Context ctx = ApplicationProvider.getApplicationContext();

        Path dir = ctx.getDataDir().toPath();
        dir = dir.resolve("tmp/test/" + this.getClass().getName() + "/config");
        int index = 0;

        for (Encryption mode : mode_list) {
            MyConfig cfg = new MyConfig();
            cfg.sk = s_key;
            cfg.kp = keypair;
            cfg.mode = mode;
            cfg.file = dir.resolve("" + index);
            this.run(cfg);
            index++;
        }
    }

    private static class MyConfig {
        Encryption mode;
        // PropertyTable data;
        Path file;
        SecretKey sk;
        KeyPair kp;
    }

    private static class ExampleDataBuilder {
        int count;
        int item_size_min;
        int item_size_max;

        PropertyTable create() {
            PropertyTable pt = PropertyTable.Factory.create();
            Random rand = SecurityRandom.getRandom();
            for (int i = 0; i < count; i++) {
                String name = "property-" + i;
                String value = this.makeRandomValue(name, i, rand);
                pt.put(name, value);
            }
            return pt;
        }

        private String makeRandomValue(String name, int i, Random rand) {
            int size = rand.nextInt(this.item_size_max);
            size = Math.min(size, this.item_size_max);
            size = Math.max(size, this.item_size_min);
            byte[] buffer = new byte[size];
            rand.nextBytes(buffer);
            return Hex.stringify(buffer);
        }
    }

    private static class ExampleModeBuilder {

        PaddingMode padding;
        CipherMode mode;
        String algorithm;

        ExampleModeBuilder init(CipherMode m, PaddingMode p) {
            this.mode = m;
            this.padding = p;
            return this;
        }

        Encryption create() {
            Encryption en = new Encryption();
            en.setPadding(this.padding);
            en.setMode(this.mode);
            en.setAlgorithm(this.algorithm);
            return en;
        }
    }

    private List<PropertyTable> listExampleData() {
        List<PropertyTable> list = new ArrayList<>();
        ExampleDataBuilder builder = new ExampleDataBuilder();

        list.add(builder.create());

        for (int i = 1; i < 10000; i *= 2) {
            builder.count = i;
            builder.item_size_min = 0;
            builder.item_size_max = 256;
            list.add(builder.create());
        }

        return list;
    }

    private List<Encryption> listExampleMode() {
        List<Encryption> list = new ArrayList<>();
        ExampleModeBuilder builder = new ExampleModeBuilder();

        list.add(builder.init(CipherMode.NONE, PaddingMode.PKCS1Padding).create());

        // 不支持这些配置:
        // list.add(builder.init(CipherMode.CBC, PaddingMode.PKCS1Padding).create());
        // list.add(builder.init(CipherMode.CBC, PaddingMode.OAEPPadding).create());
        // list.add(builder.init(CipherMode.CBC, PaddingMode.NoPadding).create());

        return list;
    }

    private void run(MyConfig cfg) throws IOException {

        //   DataAccessStack stack = DataAccessStackFactory.createStack(DataAccessStackFactory.CONFIG.TEST_KEY_CONTAINER);
        this.logConfig(cfg);

        // store
        SecretKeyFile file1 = new SecretKeyFile();
        file1.setFile(cfg.file);
        file1.setInner(cfg.sk);
        file1.setOuter(cfg.kp);
        SecretKeyFileLS.store(file1);

        // load
        SecretKeyFile file2 = new SecretKeyFile();
        file2.setFile(cfg.file);
        file2.setOuter(cfg.kp);
        file2 = SecretKeyFileLS.load(file2);

        // check
        SecretKey sk1 = file1.getInner();
        SecretKey sk2 = file2.getInner();
        this.checkSecretKey12(sk1, sk2);
    }

    private static SecretKey decodeSK(DataAccessRequest req) {
        DataAccessBlock[] b_list = req.getBlocks();
        String algorithm = "AES";
        for (DataAccessBlock block : b_list) {
            BlockType type = block.getPlainType();
            byte[] bin = block.getPlainContent();
            if (BlockType.SecretKey.equals(type)) {
                return new SecretKeySpec(bin, algorithm);
            }
        }
        // return null;
        throw new RuntimeException("no secret-key block found");
    }

    private void logConfig(MyConfig cfg) {
        StringBuilder b = new StringBuilder();
        b.append("[config:").append(this);

        //     b.append(" count_items:").append(cfg.data.names().length);

        b.append(" algorithm:").append(cfg.kp.getPublic().getAlgorithm());
        b.append(" mode:").append(cfg.mode.getMode());
        b.append(" padding:").append(cfg.mode.getPadding());
        b.append(" file:").append(cfg.file);

        b.append(']');
        Logs.info(b.toString());
    }

    private void checkSecretKey12(SecretKey sk1, SecretKey sk2) {
        byte[] bin1 = sk1.getEncoded();
        byte[] bin2 = sk2.getEncoded();
        Assert.assertArrayEquals(bin1, bin2);
    }

    private static KeyPair prepareKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024 * 4);
        return kpg.generateKeyPair();
    }

    private static SecretKey prepareSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256);
        return kg.generateKey();
    }
}
