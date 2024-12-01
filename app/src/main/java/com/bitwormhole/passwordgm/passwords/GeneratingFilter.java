package com.bitwormhole.passwordgm.passwords;

public interface GeneratingFilter {

    void generate(Generating g, GeneratingFilterChain next);

}
