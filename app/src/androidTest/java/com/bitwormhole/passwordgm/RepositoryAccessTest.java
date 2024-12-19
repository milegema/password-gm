package com.bitwormhole.passwordgm;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.RepositoryHolder;
import com.bitwormhole.passwordgm.data.repositories.RepositoryManager;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectEntity;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectHolder;
import com.bitwormhole.passwordgm.data.repositories.refs.RefHolder;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.Logs;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class RepositoryAccessTest {

    @Test
    public void useRepository() throws IOException {
        Context ctx = ApplicationProvider.getApplicationContext();
        RepositoryManager rm = RepositoryManager.getInstance(ctx);
        RepositoryHolder repo_holder = rm.getRoot();
        if (!repo_holder.exists()) {
            repo_holder.create();
        }
        Repository repo = repo_holder.fetch();
        String key = "config.test.object.ref";
        this.tryWriteObject(repo, key);
        this.tryReadObject(repo, key);
    }

    private void tryReadObject(Repository repo, String key) throws IOException {

        // load config
        PropertyTable config = repo.config().loadProperties();
        String ref_name_str = config.get(key);


        // load refs
        RefName ref_name = new RefName(ref_name_str);
        RefHolder ref_holder = repo.refs().get(ref_name);
        ObjectID oid = ref_holder.toFile().read();

        // load objects
        ObjectHolder obj_holder = repo.objects().get(oid);
        ObjectEntity entity = obj_holder.reader().read();
        PropertyTable pt = PropertyTableLS.decode(entity.getContent().toByteArray());
        String now = pt.get("now");
        Logs.info("now: " + now);
    }

    private void tryWriteObject(Repository repo, String key) throws IOException {

        PropertyTable config = repo.config().loadProperties();
        String ref_name_str = "refs/a/b/c/d";
        RefName ref_name = new RefName(ref_name_str);
        long now = System.currentTimeMillis();

        // write object
        PropertyTable pt = PropertyTable.Factory.create();
        ObjectEntity entity = new ObjectEntity();
        pt.put("now", "" + now);
        byte[] data = PropertyTableLS.encode(pt);
        entity.setType(BlockType.Properties);
        entity.setContent(new ByteSlice(data));
        entity = repo.objects().writer().write(entity);

        // write ref
        RefHolder ref = repo.refs().get(ref_name);
        ref.toFile().write(entity.getId());

        // write config
        config.put(key, ref_name_str);
        repo.config().store(config);
    }
}
