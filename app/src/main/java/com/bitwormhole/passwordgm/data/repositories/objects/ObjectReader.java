package com.bitwormhole.passwordgm.data.repositories.objects;

import java.io.IOException;

public interface ObjectReader {

    ObjectEntity read() throws IOException;

}
