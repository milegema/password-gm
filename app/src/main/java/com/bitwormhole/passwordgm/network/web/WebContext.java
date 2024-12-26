package com.bitwormhole.passwordgm.network.web;

public class WebContext {

    private String baseURL;
    private WebClient client;
    private WebClientFactory factory;
    private WebConfiguration configuration;

    public WebContext() {
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }

    public WebClientFactory getFactory() {
        return factory;
    }

    public void setFactory(WebClientFactory factory) {
        this.factory = factory;
    }

    public WebConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(WebConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
