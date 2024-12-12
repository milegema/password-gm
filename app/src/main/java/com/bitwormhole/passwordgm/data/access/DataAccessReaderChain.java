package com.bitwormhole.passwordgm.data.access;

import java.io.IOException;

public interface DataAccessReaderChain {

    void read(DataAccessRequest request) throws IOException;

}
