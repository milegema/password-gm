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

        // repo
        RepositoryManager rm = RepositoryManager.getInstance(ctx);
        RepositoryParams params = new RepositoryParams();
        params.setKeyPair(kp_holder.fetch());
        params.setScope(ContextScope.TEST);
        RepositoryHolder holder = rm.get(params);
        Repository repo;

        if (holder.exists()) {
            repo = holder.fetch();
        } else {
            repo = holder.create();
        }

        Path location = repo.location();
        Logs.info("repo.location = " + location);

    }
}
