package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.io.IOException;

public interface TableReader {

    PropertyTable read() throws IOException;

}
