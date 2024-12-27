package com.bitwormhole.passwordgm.network.web;

import com.bitwormhole.passwordgm.network.web.filters.CoreWebFilter;

import java.io.IOException;

public class DefaultWebClientFactory implements WebClientFactory {

    private static class ClientImpl implements WebClient {

        private final WebContext context;

        public ClientImpl(WebContext ctx) {
            this.context = ctx;
        }

        @Override
        public WebResponse execute(WebRequest req) throws IOException {
            WebContext ctx = req.getContext();
            if (ctx == null) {
                ctx = this.context;
                req.setContext(ctx);
            }
            return ctx.getChain().execute(req);
        }
    }

    private static WebFilterRegistration[] get_wfr_array(WebConfiguration config) {
        if (config == null) {
            return null;
        }
        return config.getFilters();
    }

    private static WebFilterRegistration[] default_wfr_array() {
        WebFilterList list = new WebFilterList();

        list.add(new CoreWebFilter(), 0);

        return list.toArray();
    }

    private WebFilterChain makeChain(WebConfiguration config) {
        WebFilterRegistration[] src = get_wfr_array(config);
        if (src == null) {
            src = default_wfr_array();
        }
        WebFilterChainBuilder builder = new WebFilterChainBuilder();
        for (WebFilterRegistration item : src) {
            builder.add(item);
        }
        return builder.create();
    }

    private static WebConfiguration default_web_config() {
        WebConfiguration cfg = new WebConfiguration();
        cfg.setFilters(default_wfr_array());
        return cfg;
    }

    @Override
    public WebClient create(WebConfiguration config) {
        if (config == null) {
            config = default_web_config();
        }
        WebContext context = new WebContext();
        context.setConfiguration(config);
        context.setClient(new ClientImpl(context));
        context.setFactory(this);
        context.setBaseURL("/");
        context.setChain(this.makeChain(config));
        return context.getClient();
    }
}
