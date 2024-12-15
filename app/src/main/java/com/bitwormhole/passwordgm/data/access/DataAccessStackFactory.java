package com.bitwormhole.passwordgm.data.access;

public final class DataAccessStackFactory {


    public enum CONFIG {
        TEST_DATA_CONTAINER,
        TEST_KEY_CONTAINER,
        MAIN_DATA_CONTAINER,
        MAIN_KEY_CONTAINER,
    }


    private DataAccessStackFactory() {
    }

    /***
     * 带缓存的 getInstance 方法
     * */
    public static DataAccessStack getStack(CONFIG cfg) {
        return createStack(cfg);
    }

    /***
     * 不带缓存的 newInstance 方法
     * */
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
        DataAccessStackConfig cfg = new DataAccessStackConfig();
        cfg.keyContainer = false;
        cfg.mock = false;
        cfg.properties = true;
        return cfg.createNewBuilder().create();
    }

    private static DataAccessStack createStack4mainKeyContainer() {
        DataAccessStackConfig cfg = new DataAccessStackConfig();
        cfg.keyContainer = true;
        cfg.mock = false;
        cfg.properties = true;
        return cfg.createNewBuilder().create();
    }

    private static DataAccessStack createStack4testDataContainer() {
        DataAccessStackConfig cfg = new DataAccessStackConfig();
        cfg.keyContainer = false;
        cfg.mock = true;
        cfg.properties = true;
        return cfg.createNewBuilder().create();
    }

    private static DataAccessStack createStack4testKeyContainer() {
        DataAccessStackConfig cfg = new DataAccessStackConfig();
        cfg.keyContainer = true;
        cfg.mock = true;
        cfg.properties = true;
        return cfg.createNewBuilder().create();
    }
}
