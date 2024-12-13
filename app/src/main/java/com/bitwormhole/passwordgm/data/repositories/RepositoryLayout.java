package com.bitwormhole.passwordgm.data.repositories;

import java.nio.file.Path;

public final class RepositoryLayout {

    private Path config; // file
    private Path sk; // file: secret-key
    private Path readme; // file: readme.txt

    private Path repository; // folder, named '.pgm'
    private Path objects; // folder
    private Path refs; // folder
    private Path tables; // folder

    private RepositoryLayout() {
    }

    public static class Builder {

        private Path dotPGM;

        public RepositoryLayout create() {
            Path base = this.dotPGM;
            RepositoryLayout l = new RepositoryLayout();
            l.config = base.resolve("config");
            l.objects = base.resolve("objects");
            l.readme = base.resolve("./../readme.txt");
            l.refs = base.resolve("refs");
            l.repository = base;
            l.sk = base.resolve("sk");
            l.tables = base.resolve("tables");
            return l;
        }

        public Path getDotPGM() {
            return dotPGM;
        }

        public void setDotPGM(Path dotPGM) {
            this.dotPGM = dotPGM;
        }
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

    public Path getSk() {
        return sk;
    }

    public void setSk(Path sk) {
        this.sk = sk;
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
