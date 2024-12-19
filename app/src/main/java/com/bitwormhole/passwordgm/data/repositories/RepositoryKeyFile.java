package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFile;
import com.bitwormhole.passwordgm.encoding.secretkeyfile.SecretKeyFileLS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class RepositoryKeyFile implements RepositoryKey {

    private final RepositoryContext context;


    public RepositoryKeyFile(RepositoryContext ctx) {
        this.context = ctx;
    }

    private Path getFile() {
        return context.getLayout().getKey();
    }


    @Override
    public boolean create() {
        try {
            // check exists
            Path path = getFile();
            if (Files.exists(path)) {
                if (Files.size(path) > 0) {
                    return false;
                }
            }
            // gen key
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256);
            SecretKey sk = kg.generateKey();

            // store key
            KeyPair kp = context.getKeyPair();
            SecretKeyFile skf = new SecretKeyFile();
            skf.setOuter(kp);
            skf.setInner(sk);
            skf.setFile(path);
            SecretKeyFileLS.store(skf);

            this.context.setSecretKey(sk);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean delete() {
        Path f = getFile();
        if (Files.isRegularFile(f)) {
            try {
                Files.delete(f);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public boolean exists() {
        return Files.exists(this.getFile());
    }

    @Override
    public SecretKey fetch() {
        SecretKey sk = this.context.getSecretKey();
        if (sk != null) {
            return sk;
        }
        // load
        SecretKeyFile skf = new SecretKeyFile();
        skf.setFile(this.getFile());
        skf.setOuter(context.getKeyPair());
        try {
            skf = SecretKeyFileLS.load(skf);
            sk = skf.getInner();
            this.context.setSecretKey(sk);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sk;
    }
}
