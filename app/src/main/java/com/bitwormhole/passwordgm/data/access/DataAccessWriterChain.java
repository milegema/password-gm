package com.bitwormhole.passwordgm.data.access;

import java.io.IOException;

public interface DataAccessWriterChain {

    void write(DataAccessRequest request) throws IOException;

}
