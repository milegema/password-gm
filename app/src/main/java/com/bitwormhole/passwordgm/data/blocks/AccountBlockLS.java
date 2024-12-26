package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.data.repositories.Repository;

public final class AccountBlockLS {

    private AccountBlockLS() {
    }


    public static UserBlock load(UserBlockID id, Repository repo) {

        throw new RuntimeException("no impl");
    }

    public static UserBlockID store(UserBlock block, Repository repo) {

        throw new RuntimeException("no impl");
    }

}
