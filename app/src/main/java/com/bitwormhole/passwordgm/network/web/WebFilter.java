package com.bitwormhole.passwordgm.network.web;

import java.io.IOException;

public interface WebFilter {

    WebResponse execute(WebRequest request, WebFilterChain next) throws IOException;

}
