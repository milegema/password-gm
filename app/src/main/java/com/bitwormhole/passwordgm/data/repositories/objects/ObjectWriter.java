package com.bitwormhole.passwordgm.data.repositories.objects;

import java.io.IOException;

public interface ObjectWriter {

    ObjectEntity write(ObjectEntity entity) throws IOException;

}
