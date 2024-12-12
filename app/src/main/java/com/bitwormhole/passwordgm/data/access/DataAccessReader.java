package com.bitwormhole.passwordgm.data.access;

import java.io.IOException;

public interface DataAccessReader {

    void read(DataAccessRequest request, DataAccessReaderChain next) throws IOException;

}
