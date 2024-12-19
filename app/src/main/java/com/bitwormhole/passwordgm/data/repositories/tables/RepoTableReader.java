package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.io.IOException;

public interface RepoTableReader {

    PropertyTable read() throws IOException;

}
