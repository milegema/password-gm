package com.bitwormhole.passwordgm.data.repositories.tables;

import java.io.IOException;

public interface RepoTableHolder {

    String name();

    boolean create();

    boolean exists();

    RepoTableReader reader() throws IOException;

    RepoTableWriter writer() throws IOException;

}
