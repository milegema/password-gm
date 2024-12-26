package com.bitwormhole.passwordgm.network.web;

import java.io.IOException;

public class DefaultWebClientFactory implements WebClientFactory {


    private static class ClientImpl implements WebClient {

        public ClientImpl(WebContext context) {
        }

        @Override
        public WebResponse execute(WebRequest req1) throws IOException {
            WebRequest req2 = new WebRequest(req1);
            WebResponse resp = new WebResponse();


            resp.setRequest(req2);
            return resp;
        }
    }

    @Override
    public WebClient create(WebConfiguration config) {
        WebContext context = new WebContext();
        context.setConfiguration(config);
        context.setClient(new ClientImpl(context));
        context.setFactory(this);
        context.setBaseURL("/");
        return context.getClient();
    }
}
