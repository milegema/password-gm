package com.bitwormhole.passwordgm.data.store;

import com.bitwormhole.passwordgm.data.ids.KeyAlias;
import com.bitwormhole.passwordgm.data.ids.UserAlias;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;
import com.bitwormhole.passwordgm.security.KeySelector;
import com.bitwormhole.passwordgm.security.SecretKeyHolder;
import com.bitwormhole.passwordgm.utils.Logs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

import javax.crypto.SecretKey;

class TableHolderFactory {

    private TableHolderFactory() {
    }

    private static class MyTableHolder implements TableHolder {

        private TableContext mContext;

        private final Path mFileAppend;
        private final Path mFileBase;
        private final UserAlias mUser;
        private final TableSelector mSelector;

        private SecretKeyHolder mSKHolder;
        private boolean mDirty;
        private boolean mInitialled;

        private PropertyTable mPropertiesA; // for append
        private PropertyTable mPropertiesB; // as base
        private PropertyTable mPropertiesW; // W = A
        private PropertyTable mPropertiesR; // R = A+B
        private final PropertyTable mPropertiesFacade; // as api


        MyTableHolder(TableContext ctx, TableFileLocation location) {

            TableSelector sel = location.getSelector();

            this.mContext = ctx;
            this.mFileAppend = location.getFileA();
            this.mFileBase = location.getFileB();
            this.mUser = sel.getUser();
            this.mSelector = new TableSelector(sel);

            this.mPropertiesFacade = new MyPropertiesFacade(this);
        }


        private void tryInit() {
            if (this.mInitialled) {
                return;
            }
            try {
                this.reload();
            } catch (Exception e) {
                Logs.warn("error: try init table fail", e);
            } finally {
                PropertyTable pt = PropertyTable.Factory.create();
                this.mPropertiesA = getPropertyTableWithoutNull(mPropertiesA, pt);
                this.mPropertiesB = getPropertyTableWithoutNull(mPropertiesB, pt);
                this.mPropertiesR = getPropertyTableWithoutNull(mPropertiesR, pt);
                this.mPropertiesW = getPropertyTableWithoutNull(mPropertiesW, pt);
                this.mInitialled = true;
            }
        }


        private SecretKey getKey() {
            return this.getKeyHolder().fetch();
        }

        private SecretKeyHolder getKeyHolder() {
            SecretKeyHolder kh = this.mSKHolder;
            if (kh == null) {
                kh = this.loadKeyHolder();
                this.mSKHolder = kh;
            }
            return kh;
        }

        private SecretKeyHolder loadKeyHolder() {
            KeyAlias key_alias = KeySelector.alias(this.mUser);
            return this.mContext.getSecretKeyManager().get(key_alias);
        }

        @Override
        public TableSelector selector() {
            return new TableSelector(this.mSelector);
        }

        @Override
        public PropertyTable properties() {
            this.tryInit();
            return this.mPropertiesFacade;
        }


        @Override
        public void reload() {
            try {
                MyTableLoader loader = new MyTableLoader(this);
                loader.load();
            } catch (Exception e) {
                throw new PGMException(PGMErrorCode.Unknown, e);
            }
        }

        @Override
        public void commit() {
            try {
                final boolean dirty = this.mDirty;
                this.mDirty = false;
                if (!dirty) {
                    return;
                }
                this.tryInit();
                MyTableSaver saver = new MyTableSaver(this);
                if (saver.isNeedForRemix()) {
                    saver.remixAB();
                    saver.saveB();
                }
                if (!saver.existsB()) {
                    saver.saveB();
                }
                saver.saveA();
            } catch (Exception e) {
                throw new PGMException(PGMErrorCode.Unknown, e);
            }
        }
    }

    private static class MyPropertiesFacade implements PropertyTable {

        private final MyTableHolder holder;

        MyPropertiesFacade(MyTableHolder h) {
            this.holder = h;
        }

        @Override
        public void put(String name, String value) {
            this.holder.mPropertiesR.put(name, value);
            this.holder.mPropertiesW.put(name, value);
            this.holder.mDirty = true;
        }

        @Override
        public String get(String name) {
            return this.holder.mPropertiesR.get(name);
        }

        @Override
        public void remove(String name) {
            this.holder.mPropertiesW.put(name, PropertyTable.REMOVED_VALUE);
            this.holder.mDirty = true;
        }

        @Override
        public void clear() {
            this.holder.mPropertiesW.clear();
            this.holder.mDirty = true;
        }

        @Override
        public int size() {
            return this.holder.mPropertiesR.size();
        }

        @Override
        public String[] names() {
            return this.holder.mPropertiesR.names();
        }

        @Override
        public Map<String, String> exportAll(Map<String, String> dst) {
            PropertyTable src1 = this.holder.mPropertiesR;
            PropertyTable src2 = this.holder.mPropertiesW;
            dst = src1.exportAll(dst);
            dst = src2.exportAll(dst);
            return dst;
        }

        @Override
        public void importAll(Map<String, String> src) {
            PropertyTable dst = this.holder.mPropertiesW;
            dst.importAll(src);
        }
    }

    private static class MyTableLoader {
        private final MyTableHolder holder;

        public MyTableLoader(MyTableHolder h) {
            this.holder = h;
        }

        public void loadA() throws IOException {
            MyTableHolder h = this.holder;
            TableFileLS ls = new TableFileLS();
            ls.setFile(h.mFileAppend);
            ls.setTable(null);
            ls.setKey(h.getKey());
            ls.load();
            h.mPropertiesA = ls.getTable();
            h.mPropertiesW = ls.getTable();
        }

        public void loadB() throws IOException {
            MyTableHolder h = this.holder;
            TableFileLS ls = new TableFileLS();
            ls.setFile(h.mFileBase);
            ls.setTable(null);
            ls.setKey(h.getKey());
            ls.load();
            h.mPropertiesB = ls.getTable();
        }

        PropertyTable mix(PropertyTable... pt_list) {
            final PropertyTable dst = PropertyTable.Factory.create();
            if (pt_list == null) {
                return dst;
            }
            for (PropertyTable pt : pt_list) {
                forEachProperty(pt, dst::put);
            }
            return dst;
        }


        public void load() throws IOException {
            this.loadB();
            this.loadA();
            MyTableHolder h = this.holder;
            h.mPropertiesR = this.mix(h.mPropertiesB, h.mPropertiesA);
        }
    }

    private static class MyTableSaver {
        private final MyTableHolder holder;

        public MyTableSaver(MyTableHolder h) {
            this.holder = h;
        }

        public boolean isNeedForRemix() {
            int threshold = 32;
            PropertyTable pt = this.holder.mPropertiesW;
            if (pt == null) {
                return false;
            }
            String[] ids = pt.names();
            if (ids == null) {
                return false;
            }
            return (ids.length > threshold);
        }

        public boolean existsB() {
            Path file = this.holder.mFileBase;
            if (file == null) {
                return false;
            }
            return Files.exists(file);
        }


        /**
         * remix: (A+B) => B; clear A;
         */
        public void remixAB() {
            final PropertyTable dstA = PropertyTable.Factory.create();// append
            final PropertyTable dstB = PropertyTable.Factory.create();// base
            final PropertyTable dstX = PropertyTable.Factory.create();// mix
            final MyTableHolder h = this.holder;
            final PropertyTable src1 = h.mPropertiesB;
            final PropertyTable src2 = h.mPropertiesA;
            forEachProperty(src1, (k, v) -> {
                dstX.put(k, v);
                dstB.put(k, v);
            });
            forEachProperty(src2, (k, v) -> {
                dstX.put(k, v);
                dstB.put(k, v);
            });
            h.mPropertiesA = dstA;
            h.mPropertiesB = dstB;
            h.mPropertiesW = dstA;
            h.mPropertiesR = dstX;
        }

        /**
         * save : append properties
         */
        public void saveA() throws IOException {
            MyTableHolder h = this.holder;
            TableFileLS ls = new TableFileLS();
            ls.setFile(h.mFileAppend);
            ls.setTable(h.mPropertiesA);
            ls.setKey(h.getKey());
            ls.save();
        }

        /**
         * save : base properties
         */
        public void saveB() throws IOException {
            MyTableHolder h = this.holder;
            TableFileLS ls = new TableFileLS();
            ls.setFile(h.mFileBase);
            ls.setTable(h.mPropertiesB);
            ls.setKey(h.getKey());
            ls.save();
        }
    }

    private interface MyPropertiesHandler {
        void onProperty(String name, String value);
    }

    private static PropertyTable getPropertyTableWithoutNull(PropertyTable... list) {
        if (list != null) {
            for (PropertyTable pt : list) {
                if (pt != null) {
                    return pt;
                }
            }
        }
        return PropertyTable.Factory.create();
    }


    private static void forEachProperty(PropertyTable src, MyPropertiesHandler h) {
        if (src == null || h == null) {
            return;
        }
        String[] ids = src.names();
        if (ids == null) {
            return;
        }
        for (String name : ids) {
            String value = src.get(name);
            if (name == null || value == null) {
                continue;
            }
            if (value.equals(PropertyTable.REMOVED_VALUE)) {
                continue;
            }
            h.onProperty(name, value);
        }
    }

    public static TableHolder createTableHolder(TableContext ctx, TableFileLocation location) {
        return new MyTableHolder(ctx, location);
    }
}
