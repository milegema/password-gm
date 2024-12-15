package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.UserAlias;
import com.bitwormhole.passwordgm.data.store.TableContext;
import com.bitwormhole.passwordgm.data.store.TableHolder;
import com.bitwormhole.passwordgm.data.store.TableManager;
import com.bitwormhole.passwordgm.data.store.TableManagerImpl;
import com.bitwormhole.passwordgm.data.store.TableName;
import com.bitwormhole.passwordgm.data.store.TableSelector;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFile;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFileLS;
import com.bitwormhole.passwordgm.security.KeyPairHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeyPairManagerImpl;
import com.bitwormhole.passwordgm.security.KeySelector;
import com.bitwormhole.passwordgm.security.SecretKeyHolder;
import com.bitwormhole.passwordgm.security.SecretKeyManager;
import com.bitwormhole.passwordgm.security.SecretKeyManagerImpl;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


@RunWith(AndroidJUnit4.class)
public class SecretKeyFileTest {

    @Test
    public void useSecretKeyFile() throws NoSuchAlgorithmException, IOException {

        Context ctx = ApplicationProvider.getApplicationContext();
        SecretKeyFile sk_file_1 = new SecretKeyFile();
        String file_name = "tmp/test/" + this.getClass().getName() + ".s-key";
        Path file = ctx.getDataDir().toPath().resolve(file_name);


        // init key-pair
        KeyPair kp = this.prepareKeyPair();
        sk_file_1.setOuter(kp);

        // init secret-key
        SecretKey sk = prepareSecretKey();
        sk_file_1.setInner(sk);


        // save secret-key
        sk_file_1.setFile(file);
        SecretKeyFileLS.store(sk_file_1);

        // load secret-key
        SecretKeyFile sk_file_2 = new SecretKeyFile();
        sk_file_2.setFile(file);
        sk_file_2.setOuter(kp);
        sk_file_2 = SecretKeyFileLS.load(sk_file_2);


        byte[] bin1 = sk_file_1.getInner().getEncoded();
        byte[] bin2 = sk_file_2.getInner().getEncoded();
        Assert.assertArrayEquals(bin1, bin2);
    }


    private SecretKey prepareSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator factory = KeyGenerator.getInstance("AES");
        factory.init(256);
        return factory.generateKey();
    }

    private KeyPair prepareKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024 * 2);
        return gen.generateKeyPair();
    }
}
