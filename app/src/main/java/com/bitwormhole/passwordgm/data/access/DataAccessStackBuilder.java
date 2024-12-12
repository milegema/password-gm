package com.bitwormhole.passwordgm.data.access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAccessStackBuilder {

    private final List<DataAccessLayer> layers;

    public DataAccessStackBuilder() {
        this.layers = new ArrayList<>();
    }

    private static interface MyChain extends DataAccessReaderChain, DataAccessWriterChain {
    }

    private static class MyNode implements MyChain {

        final MyChain next;
        final DataAccessLayer self;

        MyNode(DataAccessLayer _layer, MyChain _next) {
            this.next = _next;
            this.self = _layer;
        }

        @Override
        public void read(DataAccessRequest request) throws IOException {
            self.read(request, next);
        }

        @Override
        public void write(DataAccessRequest request) throws IOException {
            self.write(request, next);
        }
    }

    private static class MyEnding implements MyChain {
        @Override
        public void read(DataAccessRequest request) throws IOException {
            //NOP
        }

        @Override
        public void write(DataAccessRequest request) throws IOException {
            //NOP
        }
    }


    private MyChain makeChain() {
        MyChain chain = new MyEnding();
        for (DataAccessLayer layer : this.layers) {
            if (layer == null) {
                continue;
            }
            chain = new MyNode(layer, chain);
        }
        return chain;
    }


    public DataAccessStack create() {
        DataAccessStack stack = new DataAccessStack();

        MyChain chain = this.makeChain();
        stack.setReader(chain);
        stack.setWriter(chain);
        stack.setLayers(this.layers);

        return stack;
    }


    public void add(DataAccessLayer l) {
        layers.add(l);
    }
}
