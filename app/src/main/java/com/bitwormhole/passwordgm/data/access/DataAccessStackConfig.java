package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.data.access.layers.CryptoLayer;
import com.bitwormhole.passwordgm.data.access.layers.EncryptionLayerKP;
import com.bitwormhole.passwordgm.data.access.layers.EncryptionLayerSK;
import com.bitwormhole.passwordgm.data.access.layers.FileLayer;
import com.bitwormhole.passwordgm.data.access.layers.KeyContainerLayer;
import com.bitwormhole.passwordgm.data.access.layers.MockFileLayer;
import com.bitwormhole.passwordgm.data.access.layers.PemLayer;
import com.bitwormhole.passwordgm.data.access.layers.PlainLayer;
import com.bitwormhole.passwordgm.data.access.layers.PropertyLayer;

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
        b.add(this.selectPropertyLayer());
        b.add(this.selectKeyContainerLayer());

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

    private DataAccessLayer selectPropertyLayer() {
        if (this.properties) {
            return new PropertyLayer();
        }
        return null;
    }

    private DataAccessLayer selectKeyContainerLayer() {
        if (this.keyContainer) {
            return new KeyContainerLayer();
        }
        return null;
    }
}
