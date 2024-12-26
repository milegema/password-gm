package com.bitwormhole.passwordgm.boot;

import com.bitwormhole.passwordgm.components.ComLife;
import com.bitwormhole.passwordgm.components.ComLifecycle;
import com.bitwormhole.passwordgm.contexts.AppContext;
import com.bitwormhole.passwordgm.contexts.ContextAgent;
import com.bitwormhole.passwordgm.contexts.ContextFactory;
import com.bitwormhole.passwordgm.contexts.ContextHolder;
import com.bitwormhole.passwordgm.contexts.RootContext;
import com.bitwormhole.passwordgm.contexts.UserContext;
import com.bitwormhole.passwordgm.data.blocks.UserBlock;
import com.bitwormhole.passwordgm.data.blocks.UserBlockLS;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.data.repositories.refs.RefHolder;
import com.bitwormhole.passwordgm.data.repositories.refs.RefName;
import com.bitwormhole.passwordgm.security.KeyPairHolder;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class AutoUserContextLoader implements ComLifecycle {

    private ContextHolder contextHolder;

    public AutoUserContextLoader() {
    }

    public ContextHolder getContextHolder() {
        return contextHolder;
    }

    public void setContextHolder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Override
    public ComLife life() {
        ComLife l = new ComLife();
        l.setOrder(BootOrder.USER_CONTEXT);
        l.setOnCreate(this::loadAll);
        return l;
    }


    // private

    private interface PrivateLoaderFunc {
        UserContext invoke(UserContext uc) throws IOException;
    }

    private void loadAll() throws IOException {

        Logs.info("boot:loadUserContext");

        final List<PrivateLoaderFunc> steps = new ArrayList<>();
        final ContextHolder ch = this.contextHolder;
        UserContext uc = ch.getUser();

        steps.add(this::initTempUserContext);

        steps.add(this::loadUserBlock);
        steps.add(this::loadUserKeyPair);
        steps.add(this::loadUserRepo);
        steps.add(this::loadUserSecretKey);
        steps.add(this::loadUserContext);
        steps.add(this::loadUserConfig);

        for (PrivateLoaderFunc fn : steps) {
            uc = fn.invoke(uc);
        }

        ch.setUser(uc);
    }


    private UserContext loadUserConfig(UserContext uc) throws IOException {


        return uc;
    }

    private UserContext loadUserBlock(UserContext uc) throws IOException {

        final ContextHolder ch = this.contextHolder;
        final RefName user_ref_name = ch.getRoot().getConfig().getRefsBlocksUser();
        final Repository repo = ch.getRoot().getRepository();
        final RefHolder h_ref = repo.refs().get(user_ref_name);

        if (!h_ref.exists()) {
            return uc;
        }

        ObjectID user_obj_id = h_ref.read();
        UserBlockID user_block_id = new UserBlockID(ObjectID.convert(user_obj_id));
        UserBlock block = UserBlockLS.load(user_block_id, repo);
        uc.setBlock(block);
        return uc;
    }

    private UserContext loadUserKeyPair(UserContext uc) throws IOException {
        return uc;
    }

    private UserContext loadUserRepo(UserContext uc) throws IOException {
        return uc;
    }

    private UserContext loadUserSecretKey(UserContext uc) throws IOException {
        return uc;
    }

    private UserContext initTempUserContext(UserContext uc) {
        final ContextHolder ch = this.contextHolder;
        if (uc == null) {
            uc = ContextFactory.createUserContext("tmp.user", ch);
        }
        return uc;
    }


    private UserContext loadUserContext(UserContext uc1) {

        // final ContextHolder ch = this.contextHolder;
        // String alias = uc1.getAlias();
        // UserContext uc2 = UserContext.copy(uc1);


        return uc1;
    }
}
