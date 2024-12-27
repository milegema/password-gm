package com.bitwormhole.passwordgm.network.web;

public class WebConfiguration {

    private WebFilterRegistration[] filters;

    public WebConfiguration() {
    }

    public WebFilterRegistration[] getFilters() {
        return filters;
    }

    public void setFilters(WebFilterRegistration[] filters) {
        this.filters = filters;
    }
}
