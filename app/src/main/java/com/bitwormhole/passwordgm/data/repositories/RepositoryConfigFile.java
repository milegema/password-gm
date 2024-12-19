package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.access.DataAccessMode;
import com.bitwormhole.passwordgm.encoding.blocks.BlockType;
import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;
import com.bitwormhole.passwordgm.encoding.secretdatafile.SecretPropertyFile;

import java.io.IOException;
import java.nio.file.Path;

import javax.crypto.SecretKey;

public class RepositoryConfigFile implements RepositoryConfig {

    private final RepositoryContext mContext;
    private Path mFile; // cache


    public RepositoryConfigFile(RepositoryContext ctx) {
        this.mContext = ctx;
    }

    private Path getFile() {
        Path f = this.mFile;
        if (f == null) {
            f = mContext.getLayout().getConfig();
            this.mFile = f;
        }
        return f;
    }

    private SecretKey getKey() {
        return mContext.getSecretKeyManager().fetch();
    }

    @Override
    public Path file() {
        return getFile();
    }

    @Override
    public PropertyTable loadProperties() throws IOException {
        SecretPropertyFile file = new SecretPropertyFile();
        file.setDam(DataAccessMode.READONLY);
        file.setFile(getFile());
        file.setKey(getKey());
        return file.load();
    }

    @Override
    public void store(PropertyTable pt) throws IOException {
        SecretPropertyFile file = new SecretPropertyFile();
        file.setType(BlockType.Properties);
        file.setFile(getFile());
        file.setDam(DataAccessMode.REWRITE);
        file.setKey(getKey());
        file.save(pt);
    }
}
