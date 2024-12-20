package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.io.IOException;

public interface TableWriter {

    void write(PropertyTable pt) throws IOException;

    void flush() throws IOException;

}
