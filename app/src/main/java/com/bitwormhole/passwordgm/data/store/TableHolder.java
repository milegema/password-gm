package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.io.IOException;

public interface TableHolder {

    TableSelector selector();

    PropertyTable properties();

    void reload();

    void commit();

}
