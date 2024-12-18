package com.bitwormhole.passwordgm.data.repositories;

import com.bitwormhole.passwordgm.data.ids.RepositoryAlias;
import com.bitwormhole.passwordgm.data.repositories.objects.ObjectManager;
import com.bitwormhole.passwordgm.data.repositories.refs.RefManager;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public class RepositoryContext {

    // base
    private RepositoryLayout layout;
    private RepositoryAlias alias;
    private Path location;


    // keys
    private KeyPair keyPair;
    private SecretKey secretKey;
    private RepositorySecretKey secretKey2repo;


    // components
    private Repository repository;
    private ObjectManager objectManager;
    private RefManager refManager;
    private RepositoryConfig config;

    public RepositoryContext() {
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }

    public RepositoryConfig getConfig() {
        return config;
    }

    public void setConfig(RepositoryConfig config) {
        this.config = config;
    }

    public RefManager getRefManager() {
        return refManager;
    }

    public void setRefManager(RefManager refManager) {
        this.refManager = refManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public RepositorySecretKey getSecretKey2repo() {
        return secretKey2repo;
    }

    public void setSecretKey2repo(RepositorySecretKey secretKey2repo) {
        this.secretKey2repo = secretKey2repo;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
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

    public RepositoryLayout getLayout() {
        return layout;
    }

    public void setLayout(RepositoryLayout layout) {
        this.layout = layout;
    }

    public RepositoryAlias getAlias() {
        return alias;
    }

    public void setAlias(RepositoryAlias alias) {
        this.alias = alias;
    }
}
