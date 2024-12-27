package com.bitwormhole.passwordgm.network.web;

public final class WebFilterRegistration {

    private WebFilter filter;
    private int order;
    private boolean enabled;

    public WebFilterRegistration() {
        this.enabled = true;
        this.order = 0;
    }

    public WebFilter getFilter() {
        return filter;
    }

    public void setFilter(WebFilter filter) {
        this.filter = filter;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
