package com.bitwormhole.passwordgm.network.web;

public final class WebResponse {

    private final WebStatus status;
    private final WebHeaderFields header;
    private WebRequest request;
    private WebEntity entity;

    public WebResponse() {
        this.status = new WebStatus();
        this.header = new WebHeaderFields();
    }


    public WebHeaderFields getHeader() {
        return header;
    }

    public WebStatus getStatus() {
        return status;
    }


    public WebRequest getRequest() {
        return request;
    }

    public void setRequest(WebRequest request) {
        this.request = request;
    }

    public WebEntity getEntity() {
        return entity;
    }

    public void setEntity(WebEntity entity) {
        this.entity = entity;
    }
}
