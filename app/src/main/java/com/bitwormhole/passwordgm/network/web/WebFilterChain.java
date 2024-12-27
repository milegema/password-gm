package com.bitwormhole.passwordgm.network.web;

import java.io.IOException;

public interface WebFilterChain {

    WebResponse execute(WebRequest request) throws IOException;

}
