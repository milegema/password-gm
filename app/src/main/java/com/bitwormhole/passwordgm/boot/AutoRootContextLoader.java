package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;
import com.bitwormhole.passwordgm.data.blocks.RootBlock;
import com.bitwormhole.passwordgm.data.blocks.RootBlockLS;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.ids.RootBlockID;
import com.bitwormhole.passwordgm.data.properties.Names;
import com.bitwormhole.passwordgm.data.properties.pdo.ConfigLS;
import com.bitwormhole.passwordgm.data.properties.pdo.ConfigPDO;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.RepositoryConfig;
import com.bitwormhole.passwordgm.data.repositories.RepositoryHolder;
import com.bitwormhole.passwordgm.data.repositories.RepositoryKey;
import com.bitwormhole.passwordgm.data.repositories.RepositoryManager;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectEntity;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectHolder;
import com.bitwormhole.passwordgm.data.repositories.refs.Ref;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.security.KeyPairHolder;
import com.bitwormhole.passwordgm.security.KeyPairManager;
import com.bitwormhole.passwordgm.security.SecurityRandom;
import com.bitwormhole.passwordgm.utils.ByteSlice;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

public class AutoRootContextLoader implements ComLifecycle {

    private ContextHolder contextHolder;
    private KeyPairManager kpm;
    private RepositoryManager repositoryManager;

    public AutoRootContextLoader() {
    }

    public ContextHolder getContextHolder() {
        return contextHolder;
    }

    public void setContextHolder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    public KeyPairManager getKpm() {
        return kpm;
    }

    public void setKpm(KeyPairManager kpm) {
        this.kpm = kpm;
    }

    @Override
    public ComLife life() {
        ComLife l = new ComLife();
        l.setOrder(BootOrder.ROOT_CONTENT);
        l.setOnCreate(this::loadAll);
        return l;
    }


    ////////////////////////////////////////////////
    // private methods

    private interface PrivateLoaderFunc {
        RootContext invoke(RootContext rc) throws IOException;
    }


    private void loadAll() throws IOException {

        Logs.info("boot:loadRootContext");

        final ContextHolder ch = this.contextHolder;
        RootContext rc = ch.getRoot();
        List<PrivateLoaderFunc> steps = new ArrayList<>();

        steps.add(this::loadRootContext);
        steps.add(this::loadKeyPair);
        steps.add(this::loadRepository);
        steps.add(this::loadSecretKey);
        steps.add(this::loadRootConfig);
        steps.add(this::loadRootBlock);

        for (PrivateLoaderFunc fn : steps) {
            rc = fn.invoke(rc);
        }
        ch.setRoot(rc);
    }

    private RootContext loadRootContext(RootContext rc) throws IOException {
        final ContextHolder ch = this.contextHolder;
        if (rc == null) {
            rc = ContextFactory.createRootContext(ch);
            ch.setRoot(rc);
        }
        rc.setAlias("root");
        //  rc.setBlock(null);
        // rc.setFolder(repo.location().getParent());
        // rc.setBlock(this.tryLoadRootBlock(rc));
        return rc;
    }


    private RootContext loadKeyPair(RootContext rc) throws IOException {
        // key-pair
        KeyPairHolder root_kp_holder = this.kpm.getRoot();
        if (!root_kp_holder.exists()) {
            root_kp_holder.create();
        }
        KeyPair kp = root_kp_holder.fetch();
        rc.setKeyPair(kp);
        return rc;
    }

    private RootContext loadSecretKey(RootContext rc) throws IOException {
        // secret-key
        Repository repo = rc.getRepository();
        RepositoryKey repo_key = repo.key();
        if (!repo_key.exists()) {
            repo_key.create();
        }
        SecretKey sk = repo_key.fetch();
        rc.setSecretKey(sk);
        return rc;
    }

    private RootContext loadRepository(RootContext rc) throws IOException {
        // repo
        RepositoryHolder root_repo_holder = this.repositoryManager.getRoot();
        if (!root_repo_holder.exists()) {
            root_repo_holder.create();
        }
        Repository repo = root_repo_holder.fetch();
        rc.setRepository(repo);
        return rc;
    }


    private RootContext loadRootConfig(RootContext rc) throws IOException {

        boolean dirty = false;
        Repository repo = rc.getRepository();
        RepositoryConfig config = repo.config();
        PropertyTable pt = config.loadProperties();
        ConfigPDO pdo = ConfigLS.load(pt);

        if (pdo.getRefsBlocksRoot() == null) {
            pdo.setRefsBlocksRoot(new RefName("refs/blocks/root"));
            dirty = true;
        }

        if (pdo.getRefsBlocksApp() == null) {
            pdo.setRefsBlocksApp(new RefName("refs/blocks/app"));
            dirty = true;
        }

        if (pdo.getRefsBlocksUser() == null) {
            pdo.setRefsBlocksUser(new RefName("refs/blocks/user"));
            dirty = true;
        }


        if (dirty) {
            ConfigLS.store(pdo, pt);
            config.store(pt);
        }

        rc.setConfig(pdo);
        return rc;
    }


    private RootContext loadRootBlock(RootContext rc) throws IOException {

        // load refs
        Repository repo = rc.getRepository();
        RefName ref_name = rc.getConfig().getRefsBlocksRoot();
        Ref ref = repo.refs().get(ref_name);

        // load object
        RootBlock block = this.inner_loadRootBlock(rc, ref);
        if (!RootBlock.isAvailable(block)) {
            this.inner_initNewRootBlock(rc, ref);
            block = this.inner_loadRootBlock(rc, ref);
        }

        rc.setBlock(block);
        return rc;
    }


    private RootBlock inner_loadRootBlock(RootContext rc, Ref ref) throws IOException {

        if (ref == null) {
            return null;
        }
        if (!ref.exists()) {
            return null;
        }

        Repository repo = rc.getRepository();
        ObjectID obj_id = ref.read();
        RootBlockID root_block_id = new RootBlockID(ObjectID.convert(obj_id));

        // load from objects
        ObjectHolder object_holder = repo.objects().get(obj_id);
        ObjectEntity entity = object_holder.reader().read();
        PropertyTable pt2 = PropertyTableLS.decode(entity.getContent().toByteArray());

        // check result
        RootBlock block = RootBlockLS.load(root_block_id, pt2);
        if (!RootBlock.isAvailable(block)) {
            return null;
        }
        this.inner_verifyRootBlock(rc, block);
        return block;
    }

    private void inner_verifyRootBlock(RootContext rc, RootBlock block) {
        byte[] salt = block.getSalt();
        BlockType type = block.getType();
        long time = block.getCreatedAt();
        if (salt == null || type == null || time == 0) {
            throw new PGMException("invalid root block");
        }
    }

    private void inner_initNewRootBlock(RootContext rc, Ref ref) throws IOException {

        final String block_name = "root";
        final RefName ref_name = ref.name();

        Repository repo = rc.getRepository();
        long now = System.currentTimeMillis();
        RootBlock block = new RootBlock();
        byte[] salt = new byte[32 * 2];

        // prepare data

        SecurityRandom.getRandom().nextBytes(salt);

        block.setCreatedAt(now);
        block.setSalt(salt);
        block.setType(BlockType.Root);
        block.setProperties(null);
        block.setRef(ref_name);
        block.setName(block_name);

        // write to objects
        PropertyTable pt = PropertyTable.Factory.create();
        RootBlockLS.store(block, pt);
        pt.put(Names.block_name, block_name);
        byte[] data = PropertyTableLS.encode(pt);
        ObjectEntity entity = new ObjectEntity();
        entity.setType(BlockType.Root);
        entity.setContent(new ByteSlice(data));
        entity = repo.objects().writer().write(entity);
        ObjectID block_id = entity.getId();

        // write to refs
        ref.write(block_id);
    }
}
