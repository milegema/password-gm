package com.bitwormhole.passwordgm.network.web;

import java.io.IOException;

public interface WebClient {

    WebResponse execute(WebRequest request) throws IOException;

}
