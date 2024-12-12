package com.bitwormhole.passwordgm.data.access;

import java.io.IOException;

public interface DataAccessWriter {

    void write(DataAccessRequest request, DataAccessWriterChain next) throws IOException;

}
