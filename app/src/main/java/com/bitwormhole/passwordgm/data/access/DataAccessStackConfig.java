package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.data.access.layers.CryptoLayer;
import com.bitwormhole.passwordgm.data.access.layers.EncryptionLayerKP;
import com.bitwormhole.passwordgm.data.access.layers.EncryptionLayerSK;
import com.bitwormhole.passwordgm.data.access.layers.FileLayer;
import com.bitwormhole.passwordgm.data.access.layers.MockFileLayer;
import com.bitwormhole.passwordgm.data.access.layers.PemLayer;
import com.bitwormhole.passwordgm.data.access.layers.PlainLayer;

public class DataAccessStackConfig {


    public boolean mock;
    public boolean keyContainer;
    public boolean properties;


    public DataAccessStackConfig() {
    }


    public DataAccessStackBuilder createNewBuilder() {
        DataAccessStackBuilder b = new DataAccessStackBuilder();

        // layers: L --> H

        b.add(this.selectFileLayer());
        b.add(new PemLayer());
        b.add(new CryptoLayer());
        b.add(this.selectEncryptionLayer());
        b.add(new PlainLayer());

        return b;
    }

    private DataAccessLayer selectFileLayer() {
        if (this.mock) {
            return new MockFileLayer();
        }
        return new FileLayer();
    }


    private DataAccessLayer selectEncryptionLayer() {
        if (this.keyContainer) {
            return new EncryptionLayerKP();
        }
        return new EncryptionLayerSK();
    }

}
