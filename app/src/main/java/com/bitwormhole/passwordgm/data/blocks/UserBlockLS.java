package com.bitwormhole.passwordgm.data.blocks;

import com.bitwormhole.passwordgm.data.ids.AppBlockID;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.UserBlockID;
import com.bitwormhole.passwordgm.data.repositories.Repository;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

public final class UserBlockLS {

    private UserBlockLS() {
    }


    public static UserBlock load(UserBlockID id, Repository repo) {

        throw new RuntimeException("no impl");
    }

    public static UserBlockID store(UserBlock block, Repository repo) {

        throw new RuntimeException("no impl");
    }

}
