package com.bitwormhole.passwordgm.encoding.blocks;

import java.util.HashMap;
import java.util.Map;

public final class BlockTypeSet {

    private BlockTypeSet() {
    }

    private final static MyTypeCache _type_cache = new MyTypeCache();

    private static class MyTypeCache {

        private Map<String, BlockType> table;

        public Map<String, BlockType> getTable() {
            Map<String, BlockType> tab = this.table;
            if (tab == null) {
                tab = loadTable();
                this.table = tab;
            }
            return tab;
        }

        public Map<String, BlockType> loadTable() {
            BlockType[] src = all();
            Map<String, BlockType> dst = new HashMap<>();
            for (BlockType bt : src) {
                String key = normalizeName(bt.name());
                dst.put(key, bt);
            }
            return dst;
        }

        public static String normalizeName(String name) {
            if (name == null) {
                return "";
            }
            return name.trim().toLowerCase();
        }

        public synchronized BlockType findByName(final String name) {
            final String key = normalizeName(name);
            Map<String, BlockType> tab = getTable();
            BlockType value = tab.get(key);
            if (value == null) {
                return BlockType.BLOB;
            }
            return value;
        }
    }


    public static BlockType valueOf(String str) {
        BlockType bt = _type_cache.findByName(str);
        if (bt == null) {
            return BlockType.BLOB;
        }
        return bt;
    }


    public static BlockType[] all() {
        return new BlockType[]{
                BlockType.BLOB,

                BlockType.KeyPair,
                BlockType.SecretKey,

                BlockType.Properties,
                BlockType.Table,

                BlockType.Root,
                BlockType.User,
                BlockType.Domain,
                BlockType.Version,
                BlockType.Account,

                BlockType.FooBar,
        };
    }

    public static BlockType normalize(BlockType bt) {
        return normalize(bt, BlockType.BLOB);
    }

    public static BlockType normalize(BlockType bt, BlockType def) {
        if (bt == null) {
            return def;
        }
        return bt;
    }
}
