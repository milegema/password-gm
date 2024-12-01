package com.bitwormhole.passwordgm.passwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratingFilterChainBuilder {

    private final List<GeneratingFilterRegistration> filters;

    public GeneratingFilterChainBuilder() {
        this.filters = new ArrayList<>();
    }

    private static class InnerNode implements GeneratingFilterChain {
        final GeneratingFilterChain next;
        final GeneratingFilter filter;

        InnerNode(GeneratingFilter f, GeneratingFilterChain n) {
            this.filter = f;
            this.next = n;
        }

        @Override
        public void generate(Generating g) {
            this.filter.generate(g, this.next);
        }
    }

    private static class InnerEnding implements GeneratingFilterChain {
        @Override
        public void generate(Generating g) {
            // NOP
        }
    }


    public void add(GeneratingFilterRegistration reg) {
        if (reg == null) {
            return;
        }
        if (reg.filter == null) {
            return;
        }
        this.filters.add(reg);
    }

    public GeneratingFilterChain create() {
        GeneratingFilterRegistration[] src = this.filters.toArray(new GeneratingFilterRegistration[0]);
        Arrays.sort(src, (a, b) -> {
            return 0;
        });
        GeneratingFilterChain chain = new InnerEnding();
        for (GeneratingFilterRegistration reg : src) {
            chain = new InnerNode(reg.filter, chain);
        }
        return chain;
    }
}
