package com.bitwormhole.passwordgm.data.repositories.tables;

import java.io.IOException;

public interface TableHolder {

    TableName name();

    // 建表
    boolean create();

    boolean exists();

    TableReader reader() throws IOException;

    TableWriter writer() throws IOException;

}
