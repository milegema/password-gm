package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTableLS;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretDataFile;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFile;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFileLS;
import com.bitwormhole.passwordgm.errors.PGMErrorCode;
import com.bitwormhole.passwordgm.errors.PGMException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

final class RepositoryInitializer {

    public KeyPair keyPair;
    public SecretKey secretKey;
    public String email;
    public String url;

    public RepositoryInitializer() {
    }

    private static class MyFileAndFolderMaker {

        private static void checkItemNotExists(List<Path> list) {
            for (Path item : list) {
                if (Files.exists(item)) {
                    String msg = "the location of new repository is not empty";
                    throw new PGMException(msg);
                }
            }
        }

        private static void createFolders(List<Path> list) {
            try {
                for (Path dir : list) {
                    Files.createDirectories(dir);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static void createFiles(List<Path> list) {
            try {
                for (Path file : list) {
                    Files.createFile(file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        static void init_files_and_dirs(RepositoryContext ctx) throws NoSuchAlgorithmException, IOException {

            RepositoryLayout layout = ctx.getLayout();

            // prepare
            List<Path> file_list = new ArrayList<>();
            List<Path> dir_list = new ArrayList<>();

            file_list.add(layout.getKey());
            file_list.add(layout.getConfig());
            file_list.add(layout.getReadme());

            dir_list.add(layout.getRepository());
            dir_list.add(layout.getRefs());
            dir_list.add(layout.getObjects());
            dir_list.add(layout.getTables());

            // check
            checkItemNotExists(dir_list);
            checkItemNotExists(file_list);

            // create folders & files
            createFolders(dir_list);
            createFiles(file_list);
        }
    }


    public void initial(RepositoryContext ctx) throws NoSuchAlgorithmException, IOException {
        MyFileAndFolderMaker.init_files_and_dirs(ctx);
        this.generateSecretKey(ctx);
        this.createConfigFile(ctx);
    }

    private void generateSecretKey(RepositoryContext ctx) throws NoSuchAlgorithmException, IOException {
        RepositoryKey skm = ctx.getSecretKeyManager();
        skm.create();
    }

    private void createConfigFile(RepositoryContext ctx) throws IOException {

        PropertyTable pt = PropertyTable.Factory.create();
        pt.put("public-key.fingerprint", "todo...");
        pt.put("user.email", this.email);
        pt.put("service.url", this.url);

        pt.put("core.block",  "refs/blocks/user/info" );

        ctx.getConfig().store(pt);
    }
}
