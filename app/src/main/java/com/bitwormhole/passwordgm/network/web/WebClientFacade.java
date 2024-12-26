package com.bitwormhole.passwordgm.network.web;

import java.io.IOException;

public class WebClientFacade implements WebClient {

    private WebClient innerWebClient;

    public WebClientFacade() {
    }

    private WebClient getInner() {
        WebClient i = this.innerWebClient;
        if (i == null) {
            i = this.loadInner();
            this.innerWebClient = i;
        }
        return i;
    }

    private WebClient loadInner() {
        WebClientFactory factory = new DefaultWebClientFactory();
        WebConfiguration cfg = new WebConfiguration();
        return factory.create(cfg);
    }

    @Override
    public WebResponse execute(WebRequest request) throws IOException {
        return this.getInner().execute(request);
    }
}
