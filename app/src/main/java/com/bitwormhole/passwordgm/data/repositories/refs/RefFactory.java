package com.bitwormhole.passwordgm.data.repositories.refs;

import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.data.ids.BlockID;
import com.bitwormhole.passwordgm.data.ids.ObjectID;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretTextFile;
import com.bitwormhole.passwordgm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RefFactory {

    private static class MyHolder implements RefHolder {
        final RefContext context;

        public MyHolder(RefContext ctx) {
            this.context = ctx;
        }

        @Override
        public RefFile toFile() {
            return context.getFile();
        }

        @Override
        public RefFolder toFolder() {
            return context.getFolder();
        }

        @Override
        public boolean isFile() {
            return Files.isRegularFile(context.getPath());
        }

        @Override
        public boolean isFolder() {
            return Files.isDirectory(context.getPath());
        }

        @Override
        public RefName name() {
            return context.getName();
        }

        @Override
        public boolean exists() {
            return Files.exists(context.getPath());
        }

        @Override
        public ObjectID read() throws IOException {
            return this.context.getFile().read();
        }

        @Override
        public void write(ObjectID id) throws IOException {
            this.context.getFile().write(id);
        }
    }

    private static class MyFile implements RefFile {
        final RefContext context;

        public MyFile(RefContext ctx) {
            this.context = ctx;
        }

        @Override
        public RefName name() {
            return context.getHolder().name();
        }

        @Override
        public boolean exists() {
            return context.getHolder().exists();
        }

        @Override
        public ObjectID read() throws IOException {

            SecretTextFile file = new SecretTextFile();
            file.setFile(this.context.getPath());
            file.setKey(this.context.getKey());
            file.setDam(DataAccessMode.READONLY);

            String text = file.load();
            BlockID bid = new BlockID(text.trim());
            return ObjectID.convert(bid);
        }

        @Override
        public void write(ObjectID id) throws IOException {

            SecretTextFile file = new SecretTextFile();
            file.setFile(this.context.getPath());
            file.setKey(this.context.getKey());
            file.setDam(DataAccessMode.READONLY);

            String text = id.toString();
            file.save(text);
        }
    }

    private static class MyFolder implements RefFolder {
        final RefContext context;

        public MyFolder(RefContext ctx) {
            this.context = ctx;
        }

        @Override
        public String[] items() {
            return context.getPath().toFile().list();
        }

        @Override
        public RefName[] listItemNames() {
            String[] src = this.items();
            RefName[] dst = new RefName[src.length];
            RefName myName = context.getName();
            for (int i = 0; i < src.length; i++) {
                String itemName = src[i];
                dst[i] = new RefName(myName + "/" + itemName);
            }
            return dst;
        }

        @Override
        public RefName name() {
            return context.getHolder().name();
        }

        @Override
        public boolean exists() {
            return context.getHolder().exists();
        }

        @Override
        public ObjectID read() throws IOException {
            Path p = this.context.getPath();
            if (!Files.isRegularFile(p)) {
                return null;
            }
            SecretTextFile file = new SecretTextFile();
            file.setKey(this.context.getKey());
            file.setFile(p);
            String str = file.load().trim();
            if (str.isEmpty()) {
                return null;
            }
            BlockID block_id = new BlockID(str);
            return ObjectID.convert(block_id);
        }

        @Override
        public void write(ObjectID id) throws IOException {

            String str = id.toString();
            Path p = this.context.getPath();
            FileUtils.mkdirsForFile(p);

            SecretTextFile file = new SecretTextFile();
            file.setKey(this.context.getKey());
            file.setDam(DataAccessMode.REWRITE);
            file.setFile(p);
            file.save(str);
        }
    }


    public static void create(RefContext ctx) {
        ctx.setHolder(new MyHolder(ctx));
        ctx.setFile(new MyFile(ctx));
        ctx.setFolder(new MyFolder(ctx));
    }
}
