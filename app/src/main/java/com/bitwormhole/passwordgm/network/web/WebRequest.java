package com.bitwormhole.passwordgm.network.web;

public final class WebRequest {

    private WebMethod method;
    private String url;
    private final WebHeaderFields header;
    private WebEntity entity;

    public WebRequest() {
        this.header = new WebHeaderFields();
        this.method = WebMethod.GET;
        this.url = "/";
    }

    public WebRequest(WebRequest src) {
        if (src == null) {
            this.header = new WebHeaderFields();
        } else {
            this.header = new WebHeaderFields(src.header);
            this.method = src.method;
            this.url = src.url;
            this.entity = src.entity;
        }
    }

    public WebMethod getMethod() {
        return method;
    }

    public void setMethod(WebMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebEntity getEntity() {
        return entity;
    }

    public void setEntity(WebEntity entity) {
        this.entity = entity;
    }

    public WebHeaderFields getHeader() {
        return header;
    }
}
