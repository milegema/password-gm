package com.bitwormhole.passwordgm.contexts;

import com.bitwormhole.passwordgm.security.SecretFileIO;

import java.nio.file.Path;
import java.security.KeyPair;

import javax.crypto.SecretKey;

public class ContextBase {

    private final ContextBase parent;
    private final ContextScope scope;
    private Path folder;
    private SecretFileIO fileIO;
    private String name;
    private String label;
    private String description;
    private SecretKey secretKey;
    private KeyPair keyPair;


    public ContextBase(ContextBase _parent, ContextScope _scope) {
        this.parent = _parent;
        this.scope = _scope;
    }

    public ContextBase getParent() {
        return parent;
    }

    public ContextScope getScope() {
        return scope;
    }

    public Path getFolder() {
        return folder;
    }

    public void setFolder(Path folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SecretFileIO getFileIO() {
        return fileIO;
    }

    public void setFileIO(SecretFileIO fileIO) {
        this.fileIO = fileIO;
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
}
