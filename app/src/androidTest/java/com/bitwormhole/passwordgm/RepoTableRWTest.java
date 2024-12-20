package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.RepositoryHolder;
import com.bitwormhole.passwordgm.data.repositories.RepositoryManager;
import com.bitwormhole.passwordgm.data.repositories.tables.TableHolder;
import com.bitwormhole.passwordgm.data.repositories.tables.TableManager;
import com.bitwormhole.passwordgm.data.repositories.tables.TableName;
import com.bitwormhole.passwordgm.data.repositories.tables.TableWriter;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class RepoTableRWTest {

    @Test
    public void useRepoTable() throws IOException {

        Context ctx = ApplicationProvider.getApplicationContext();
        RepositoryManager rm = RepositoryManager.getInstance(ctx);
        RepositoryHolder repo_holder = rm.getRoot();


        if (!repo_holder.exists()) {
            repo_holder.create();
        }
        Repository repo = repo_holder.fetch();
        TableManager tm = repo.tables();
        TableHolder tab_holder = tm.get(new TableName("test1"));
        TableName name = tab_holder.name();

        if (!tab_holder.exists()) {
            tab_holder.create();
        }


        for (int i = 0; i < 20; i++) {
            PropertyTable out = PropertyTable.Factory.create();
            out.put("item." + i + ".id", "" + i);
            out.put("item." + i + ".name", "test_item_" + i);
            out.put("item." + i + ".description", "foo bar");

            tab_holder = tm.get(name);
            tab_holder.reader().read();
            TableWriter wtr = tab_holder.writer();
            wtr.write(out);
            wtr.flush();
        }

        tab_holder = tm.get(name);
        PropertyTable in = tab_holder.reader().read();
        String str = PropertyTableLS.stringify(in);
        Logs.debug("final table properties: " + str);
    }
}
