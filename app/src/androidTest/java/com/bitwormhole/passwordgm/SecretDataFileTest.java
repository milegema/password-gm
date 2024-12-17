package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretBinaryFile;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretPropertyFile;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretTextFile;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


@RunWith(AndroidJUnit4.class)
public class SecretDataFileTest {

    @Test
    public void useSecretDataFileText() throws IOException, NoSuchAlgorithmException {

        Path dir = getWorkingDir();
        SecretKey sk = getSecretKey();
        SecretTextFile file = new SecretTextFile();
        file.setFile(dir.resolve("demo.txt"));
        file.setKey(sk);
        file.setType(BlockType.FooBar);

        String str1 = "hello,foo,bar";
        file.save(str1);
        String str2 = file.load();

        Assert.assertEquals(str1, str2);
    }

    @Test
    public void useSecretDataFileBinary() throws IOException, NoSuchAlgorithmException {

        Path dir = getWorkingDir();
        SecretKey sk = getSecretKey();
        SecretBinaryFile file = new SecretBinaryFile();
        file.setFile(dir.resolve("demo.dat"));
        file.setKey(sk);
        file.setType(BlockType.BLOB);

        byte[] bin1 = new byte[]{1, 3, 5, 7, 92, 46, 80};
        file.save(bin1);
        byte[] bin2 = file.load();

        Assert.assertArrayEquals(bin1, bin2);
    }

    @Test
    public void useSecretDataFileProperty() throws IOException, NoSuchAlgorithmException {

        Path dir = getWorkingDir();
        SecretKey sk = getSecretKey();
        SecretPropertyFile file = new SecretPropertyFile();
        file.setFile(dir.resolve("demo.prop"));
        file.setKey(sk);
        file.setType(BlockType.Properties);

        PropertyTable pt1 = PropertyTable.Factory.create();
        pt1.put("a", "1");
        pt1.put("b", "2");
        pt1.put("c", "3");
        file.save(pt1);
        PropertyTable pt2 = file.load();

        String str1 = PropertyTableLS.stringify(pt1);
        String str2 = PropertyTableLS.stringify(pt2);
        Assert.assertEquals(str1, str2);
    }

    private Path getWorkingDir() {
        Context ctx = ApplicationProvider.getApplicationContext();
        return ctx.getDataDir().toPath().resolve("tmp/test/wd");
    }

    private SecretKey getSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256);
        return kg.generateKey();
    }
}
