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

final class RepositoryInit {

    private KeyPair keyPair;
    private SecretKey secretKey;
    private String email;
    private String url;

    public RepositoryInit() {
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void init(RepositoryLayout layout) throws NoSuchAlgorithmException, IOException {

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
        this.checkItemNotExists(dir_list);
        this.checkItemNotExists(file_list);

        // create folders & files
        this.createFolders(dir_list);
        this.createFiles(file_list);
        this.generateSecretKey(layout);
        this.createConfigFile(layout);
    }

    private void generateSecretKey(RepositoryLayout layout) throws NoSuchAlgorithmException, IOException {
        SecretKey key = this.secretKey;
        if (key == null) {
            String algorithm = "AES";
            int size = 256;
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            kg.init(size);
            key = kg.generateKey();
            this.secretKey = key;
        }

        SecretKeyFile file = new SecretKeyFile();
        file.setFile(layout.getKey());
        file.setInner(key);
        file.setOuter(this.keyPair);

        SecretKeyFileLS.store(file);
    }

    private void createConfigFile(RepositoryLayout layout) throws IOException {

        SecretDataFile file = new SecretDataFile();
        file.setFile(layout.getConfig());
        file.setKey(this.secretKey);


        PropertyTable pt = PropertyTable.Factory.create();
        pt.put("public-key.fingerprint", "todo...");
        pt.put("user.email", this.email);
        pt.put("service.url", this.url);


        byte[] bin = PropertyTableLS.encode(pt);
        file.write(bin);
    }

    private void checkItemNotExists(List<Path> list) {
        for (Path item : list) {
            if (Files.exists(item)) {
                String msg = "the location of new repository is not empty";
                throw new PGMException(PGMErrorCode.Unknown, msg);
            }
        }
    }

    private void createFolders(List<Path> list) {
        try {
            for (Path dir : list) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFiles(List<Path> list) {
        try {
            for (Path file : list) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
