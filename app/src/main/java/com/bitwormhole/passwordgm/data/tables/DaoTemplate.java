package com.bitwormhole.passwordgm.data.tables;

import java.util.List;

public interface DaoTemplate<ENTITY> {

    interface Callback<ENTITY> {
        boolean accept(ENTITY item);
    }

    ENTITY insert(ENTITY item);

    ENTITY update(long id, Callback<ENTITY> cb);

    boolean remove(long id);

    ENTITY find(long id);

    List<ENTITY> query(Callback<ENTITY> cb);
}
