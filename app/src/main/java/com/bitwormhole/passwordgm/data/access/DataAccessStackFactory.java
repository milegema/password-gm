package com.bitwormhole.passwordgm.data.access;

import com.bitwormhole.passwordgm.data.access.layers.CryptoLayer;
import com.bitwormhole.passwordgm.data.access.layers.EncryptionLayerKP;
import com.bitwormhole.passwordgm.data.access.layers.EncryptionLayerSK;
import com.bitwormhole.passwordgm.data.access.layers.FileLayer;
import com.bitwormhole.passwordgm.data.access.layers.KeyContainerLayer;
import com.bitwormhole.passwordgm.data.access.layers.MockFileLayer;
import com.bitwormhole.passwordgm.data.access.layers.PemLayer;
import com.bitwormhole.passwordgm.data.access.layers.PropertyLayer;

public final class DataAccessStackFactory {


    public enum CONFIG {
        TEST_DATA_CONTAINER,
        TEST_KEY_CONTAINER,
        MAIN_DATA_CONTAINER,
        MAIN_KEY_CONTAINER,
    }


    private DataAccessStackFactory() {
    }

    public static DataAccessStack createStack(CONFIG cfg) {
        if (cfg == null) {
            cfg = CONFIG.MAIN_DATA_CONTAINER;
        }
        switch (cfg) {
            case MAIN_KEY_CONTAINER:
                return createStack4mainKeyContainer();
            case MAIN_DATA_CONTAINER:
                return createStack4mainDataContainer();
            case TEST_KEY_CONTAINER:
                return createStack4testKeyContainer();
            case TEST_DATA_CONTAINER:
                return createStack4testDataContainer();
            default:
                break;
        }
        return createStack4mainDataContainer();
    }


    private static DataAccessStack createStack4mainDataContainer() {

        DataAccessStackBuilder b = new DataAccessStackBuilder();

        // layers: L --> H
        b.add(new FileLayer());
        b.add(new PemLayer());
        b.add(new CryptoLayer());
        b.add(new EncryptionLayerSK());
        b.add(new PropertyLayer());


        return b.create();
    }

    private static DataAccessStack createStack4mainKeyContainer() {

        DataAccessStackBuilder b = new DataAccessStackBuilder();

        // layers: L --> H
        b.add(new FileLayer());
        b.add(new PemLayer());
        b.add(new CryptoLayer());
        b.add(new EncryptionLayerKP());
        b.add(new PropertyLayer());
        b.add(new KeyContainerLayer());

        return b.create();
    }

    private static DataAccessStack createStack4testDataContainer() {

        DataAccessStackBuilder b = new DataAccessStackBuilder();

        // layers: L --> H
        b.add(new MockFileLayer());
        b.add(new PemLayer());
        b.add(new CryptoLayer());
        b.add(new EncryptionLayerSK());
        b.add(new PropertyLayer());

        return b.create();
    }

    private static DataAccessStack createStack4testKeyContainer() {

        DataAccessStackBuilder b = new DataAccessStackBuilder();

        // layers: L --> H
        b.add(new MockFileLayer());
        b.add(new PemLayer());
        b.add(new CryptoLayer());
        b.add(new EncryptionLayerKP());
        b.add(new PropertyLayer());
        b.add(new KeyContainerLayer());

        return b.create();
    }
}
