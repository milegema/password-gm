package com.bitwormhole.passwordgm.data.repositories.tables;

import com.bitwormhole.passwordgm.encoding.ptable.PropertyTable;

import java.nio.file.Path;
import java.util.concurrent.Executor;

import javax.crypto.SecretKey;

final class TableContext {

    private TableName name;
    private TableReader reader;
    private TableWriter writer;
    private TableHolder holder;
    private Path file;
    private SecretKey key;
    private Executor synchronizedWorker;
    private boolean needRewrite;


    private PropertyTable cache; // for reader
    private PropertyTable buffer; // for writer


    public TableContext() {
    }

    public Executor getSynchronizedWorker() {
        return synchronizedWorker;
    }

    public void setSynchronizedWorker(Executor synchronizedWorker) {
        this.synchronizedWorker = synchronizedWorker;
    }

    public TableName getName() {
        return name;
    }

    public void setName(TableName name) {
        this.name = name;
    }

    public TableReader getReader() {
        return reader;
    }

    public void setReader(TableReader reader) {
        this.reader = reader;
    }

    public TableWriter getWriter() {
        return writer;
    }

    public void setWriter(TableWriter writer) {
        this.writer = writer;
    }

    public Path getFile() {
        return file;
    }

    public void setFile(Path file) {
        this.file = file;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public TableHolder getHolder() {
        return holder;
    }

    public void setHolder(TableHolder holder) {
        this.holder = holder;
    }


    public PropertyTable getBuffer() {
        return buffer;
    }

    public void setBuffer(PropertyTable buffer) {
        this.buffer = buffer;
    }

    public PropertyTable getCache() {
        return cache;
    }

    public void setCache(PropertyTable cache) {
        this.cache = cache;
    }


    public boolean isNeedRewrite() {
        return needRewrite;
    }

    public void setNeedRewrite(boolean needRewrite) {
        this.needRewrite = needRewrite;
    }
}
