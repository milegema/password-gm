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
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretDataFile;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretDataFileLS;
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
    public void useSecretDataFile() throws IOException {


        // todo ...
        SecretDataFile sd_file = new SecretDataFile();
        SecretDataFileLS.save(sd_file);

    }


}
