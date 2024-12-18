package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.contexts.ContextScope;
import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.RepositoryHolder;
import com.bitwormhole.passwordgm.data.repositories.RepositoryManager;
import com.bitwormhole.passwordgm.data.repositories.RepositoryParams;
import com.bitwormhole.passwordgm.security.KeyPairHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.KeyPairManagerImpl;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.file.Path;
import java.security.KeyPair;


@RunWith(AndroidJUnit4.class)
public class RepositoryManagerTest {

    @Test
    public void useRepositoryManager() {


        Context ctx = ApplicationProvider.getApplicationContext();

        // keypair
        KeyPairManager kpm = new KeyPairManagerImpl();
        KeyPairHolder kp_holder = kpm.get(new KeyAlias("test"));
        if (!kp_holder.exists()) {
            kp_holder.create();
        }
        KeyPair kp = kp_holder.fetch();

        // repo
        RepositoryManager rm = RepositoryManager.getInstance(ctx);
        RepositoryHolder holder = rm.get(kp);
        Repository repo;

        if (holder.exists()) {
            repo = holder.fetch();
        } else {
            repo = holder.create();
        }

        Path location = repo.location();
        Logs.info("repo.location = " + location);

    }

    @Test
    public void useRepositoryManagerGetRoot() {

        Context ctx = ApplicationProvider.getApplicationContext();
        RepositoryManager rm = RepositoryManager.getInstance(ctx);
        RepositoryHolder holder = rm.getRoot();
        Repository repo = null;
        if (!holder.exists()) {
            repo = holder.create();
        } else {
            repo = holder.fetch();
        }

        repo.location();
    }
}
