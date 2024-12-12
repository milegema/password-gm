package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeyPairManagerImpl;
import com.bitwormhole.passwordgm.security.KeySelector;
import com.bitwormhole.passwordgm.security.SecretKeyHolder;
import com.bitwormhole.passwordgm.security.SecretKeyManagerImpl;
import com.bitwormhole.passwordgm.utils.HashUtils;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.file.Path;

import javax.crypto.SecretKey;


@RunWith(AndroidJUnit4.class)
public class SecretKeyManagerTest {

    @Test
    public void useSecretKeyManager() {

        Context ctx = ApplicationProvider.getApplicationContext();
        Path datadir = ctx.getDataDir().toPath();
        Path folder = datadir.resolve(".test/secret-keys/");

        KeyPairManager kpm = new KeyPairManagerImpl();

        SecretKeyManagerImpl skm = new SecretKeyManagerImpl();
        skm.setKeyPairManager(kpm);
        skm.setSecretKeysFolder(folder);


        KeyAlias alias = KeySelector.alias(ContextScope.TEST, this + "v3");
        SecretKeyHolder h1 = skm.get(alias);
        SecretKeyHolder h2 = skm.get(alias);

        // store
        if (!h1.exists()) {
            h1.create();
        }

        SecretKey sk1 = h1.fetch();
        SecretKey sk2 = h2.fetch();

        Logs.info("key.alias = " + h2.alias());
        logSecretKey("SecretKey-1", sk1);
        logSecretKey("SecretKey-2", sk2);
    }

    private static void logSecretKey(String tag, SecretKey sk) {
        byte[] bin = sk.getEncoded();
        String hex = HashUtils.hexSum(bin, HashUtils.SHA256);
        Logs.info(tag + ".fingerprint = " + hex);
    }
}
