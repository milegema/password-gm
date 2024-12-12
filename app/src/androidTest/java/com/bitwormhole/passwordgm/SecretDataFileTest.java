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
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeyPairManagerImpl;
import com.bitwormhole.passwordgm.security.KeySelector;
import com.bitwormhole.passwordgm.security.SecretKeyHolder;
import com.bitwormhole.passwordgm.security.SecretKeyManager;
import com.bitwormhole.passwordgm.security.SecretKeyManagerImpl;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Path;


@RunWith(AndroidJUnit4.class)
public class SecretDataFileTest {

    @Test
    public void useTableManager() {


        TableSelector sel = new TableSelector();
        sel.setScope(ContextScope.TEST);
        sel.setUser(new UserAlias("test"));
        sel.setTable(TableName.test1);

        TableManager tm = this.initTableManager(sel.getUser());
        TableHolder th1 = tm.getTable(sel);
        TableHolder th2 = tm.getTable(sel);
        TableHolder th3 = tm.getTable(sel);
        TableHolder th4 = tm.getTable(sel);

        // write th1
        PropertyTable pt = th1.properties();
        pt.put("a", "1");
        pt.put("b", "2");
        pt.put("c", "3");
        th1.commit();

        // read th2
        th2.reload();
        this.logTable("TableHolder-2", th2);

        // write th3
        th3.reload();
        pt = th3.properties();
        pt.put("aa", "11");
        pt.put("bb", "22");
        pt.put("cc", "33");
        th3.commit();

        // read th4
        th4.reload();
        this.logTable("TableHolder-4", th4);
    }

    private void logTable(String tag, TableHolder th) {
        PropertyTable pt = th.properties();
        String[] ids = pt.names();
        Logs.info(tag + " - begin");
        for (String name : ids) {
            String value = pt.get(name);
            Logs.info("\t" + name + " = " + value);
        }
        Logs.info(tag + " - end");
    }


    private TableManager initTableManager(UserAlias user) {
        Context ctx = ApplicationProvider.getApplicationContext();
        KeyPairManager kpm = new KeyPairManagerImpl();
        SecretKeyManagerImpl skm = new SecretKeyManagerImpl();
        TableContext table_context = new TableContext();
        TableManagerImpl tm = new TableManagerImpl();

        Path dir = ctx.getDataDir().toPath().resolve(".test");

        skm.setKeyPairManager(kpm);
        skm.setSecretKeysFolder(dir.resolve("keys"));

        table_context.setBaseDir(dir);
        table_context.setSecretKeyManager(skm);


        tm.setContext(table_context);


        this.prepareKeys(skm, user);
        return tm;
    }

    private void prepareKeys(SecretKeyManager skm, UserAlias user) {
        SecretKeyHolder skh = skm.get(KeySelector.alias(user));
        if (!skh.exists()) {
            skh.create();
        }
    }
}
