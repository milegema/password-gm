package com.bitwormhole.passwordgm.data.access;

import java.util.List;

public class DataAccessStack {

    private List<DataAccessLayer> layers;
    private DataAccessReaderChain reader;
    private DataAccessWriterChain writer;
    private DataAccessRequestFactory requestFactory;

    public DataAccessStack() {
    }

    public DataAccessRequestFactory getRequestFactory() {
        return requestFactory;
    }

    public void setRequestFactory(DataAccessRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public DataAccessWriterChain getWriter() {
        return writer;
    }

    public void setWriter(DataAccessWriterChain writer) {
        this.writer = writer;
    }

    public DataAccessReaderChain getReader() {
        return reader;
    }

    public void setReader(DataAccessReaderChain reader) {
        this.reader = reader;
    }

    public List<DataAccessLayer> getLayers() {
        return layers;
    }

    public void setLayers(List<DataAccessLayer> layers) {
        this.layers = layers;
    }
}
