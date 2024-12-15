package com.bitwormhole.passwordgm;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.data.access.DataAccessBlock;
import com.bitwormhole.passwordgm.data.access.DataAccessRequest;
import com.bitwormhole.passwordgm.data.access.DataAccessStack;
import com.bitwormhole.passwordgm.data.access.DataAccessStackFactory;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RunWith(AndroidJUnit4.class)
public class DataContainerTest {

    @Test
    public void useDataContainerStack() throws IOException, NoSuchAlgorithmException {

        SecretKey key = prepareKey();
        List<PropertyTable> data_list = listExampleData();
        List<Encryption> mode_list = listExampleMode();
        int index = 0;
        Path dir = Paths.get("/tmp/test/" + this.getClass().getName() + "/config");

        for (Encryption mode : mode_list) {
            for (PropertyTable data : data_list) {
                MyConfig cfg = new MyConfig();
                cfg.key = key;
                cfg.data = data;
                cfg.mode = mode;
                cfg.file = dir.resolve("" + index);
                this.run(cfg);
                index++;
            }
        }
    }

    private static class MyConfig {
        Encryption mode;
        PropertyTable data;
        Path file;
        SecretKey key;
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

        list.add(builder.init(CipherMode.CBC, PaddingMode.PKCS7Padding).create());
        list.add(builder.init(CipherMode.CBC, PaddingMode.PKCS5Padding).create());

        list.add(builder.init(CipherMode.ECB, PaddingMode.PKCS7Padding).create());
        list.add(builder.init(CipherMode.CTR, PaddingMode.PKCS7Padding).create());
        list.add(builder.init(CipherMode.OFB, PaddingMode.PKCS7Padding).create());
        list.add(builder.init(CipherMode.CFB, PaddingMode.PKCS7Padding).create());

        // 不支持这些配置:
        // list.add(builder.init(CipherMode.CBC, PaddingMode.PKCS1Padding).create());
        // list.add(builder.init(CipherMode.CBC, PaddingMode.OAEPPadding).create());
        // list.add(builder.init(CipherMode.CBC, PaddingMode.NoPadding).create());

        return list;
    }

    private void run(MyConfig cfg) throws IOException {

        DataAccessStack stack = DataAccessStackFactory.createStack(DataAccessStackFactory.CONFIG.TEST_DATA_CONTAINER);
        PropertyTable pt1 = cfg.data;
        this.logConfig(cfg);

        // write
        DataAccessRequest req1 = new DataAccessRequest();
        req1.setFile(cfg.file);
        req1.setSecretKey(cfg.key);
        req1.setBlocks(encode(pt1));
        req1.setMode(cfg.mode.getMode());
        req1.setPadding(cfg.mode.getPadding());
        stack.getWriter().write(req1);

        // read
        DataAccessRequest req2 = new DataAccessRequest();
        req2.setFile(cfg.file);
        req2.setSecretKey(cfg.key);
        stack.getReader().read(req2);
        PropertyTable pt2 = decode(req2.getBlocks());

        this.checkPropertyTable12(pt1, pt2);
    }

    private static DataAccessBlock[] encode(PropertyTable src) {
        byte[] bin = PropertyTableLS.encode(src);
        DataAccessBlock block = new DataAccessBlock();
        block.setPlain(BlockType.Properties, bin);
        return new DataAccessBlock[]{block};
    }

    private static PropertyTable decode(DataAccessBlock[] src) {
        Map<String, String> tmp = new HashMap<>();
        for (DataAccessBlock item : src) {
            byte[] bin = item.getPlainContent();
            PropertyTable pt1 = PropertyTableLS.decode(bin);
            tmp = pt1.exportAll(tmp);
        }
        PropertyTable pt2 = PropertyTable.Factory.create();
        pt2.importAll(tmp);
        return pt2;
    }

    private void logConfig(MyConfig cfg) {
        StringBuilder b = new StringBuilder();
        b.append("[config:").append(this);

        b.append(" count_items:").append(cfg.data.names().length);

        b.append(" algorithm:").append(cfg.key.getAlgorithm());
        b.append(" mode:").append(cfg.mode.getMode());
        b.append(" padding:").append(cfg.mode.getPadding());
        b.append(" file:").append(cfg.file);

        b.append(']');
        Logs.info(b.toString());
    }

    private void checkPropertyTable12(PropertyTable pt1, PropertyTable pt2) {
        String[] name_list1 = pt1.names();
        String[] name_list2 = pt2.names();
        Assert.assertEquals(name_list1.length, name_list2.length);
        for (String id : name_list1) {
            String value1 = pt1.get(id);
            String value2 = pt2.get(id);
            Assert.assertEquals(value1, value2);
        }
    }

    private static SecretKey prepareKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256);
        return kg.generateKey();
    }
}
