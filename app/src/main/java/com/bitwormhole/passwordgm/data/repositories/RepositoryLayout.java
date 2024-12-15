package com.bitwormhole.passwordgm.data.repositories;

import java.nio.file.Path;

public final class RepositoryLayout {


    public final static String REGULAR_REPO_FOLDER_NAME = ".passwordgm";


    // files
    private Path config; // file
    private Path key; // file: secret-key
    private Path readme; // file: readme.txt

    // folders
    private Path repository; // folder, named '.passwordgm'
    private Path objects; // folder
    private Path refs; // folder
    private Path tables; // folder

    private RepositoryLayout() {
    }

    public static class Builder {

        private Path dotPasswordGM;

        public RepositoryLayout create() {
            Path base = this.dotPasswordGM.normalize();
            RepositoryLayout l = new RepositoryLayout();
            l.config = base.resolve("config");
            l.objects = base.resolve("objects");
            l.readme = base.resolve("readme.txt");
            l.refs = base.resolve("refs");
            l.repository = base;
            l.key = base.resolve("key");
            l.tables = base.resolve("tables");
            return l;
        }


        public Path getDotPasswordGM() {
            return dotPasswordGM;
        }

        public void setDotPasswordGM(Path dotPasswordGM) {
            this.dotPasswordGM = dotPasswordGM;
        }
    }

    public Path getReadme() {
        return readme;
    }

    public void setReadme(Path readme) {
        this.readme = readme;
    }

    public Path getTables() {
        return tables;
    }

    public void setTables(Path tables) {
        this.tables = tables;
    }

    public Path getRepository() {
        return repository;
    }

    public void setRepository(Path repository) {
        this.repository = repository;
    }

    public Path getConfig() {
        return config;
    }

    public void setConfig(Path config) {
        this.config = config;
    }

    public Path getKey() {
        return key;
    }

    public void setKey(Path sk) {
        this.key = sk;
    }

    public Path getObjects() {
        return objects;
    }

    public void setObjects(Path objects) {
        this.objects = objects;
    }

    public Path getRefs() {
        return refs;
    }

    public void setRefs(Path refs) {
        this.refs = refs;
    }
}
