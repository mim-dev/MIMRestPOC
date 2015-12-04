package com.mim_development.android.rest.mimrest.model.services.base.http.connection;


public class HttpConnection {

    private String server;
    private String path;
    private String action;
    private boolean secure;

    protected String getServer() {
        return server;
    }

    protected String getPath() {
        return path;
    }

    protected String getAction() {
        return action;
    }

    protected boolean isSecure() {
        return secure;
    }

    public HttpConnection(boolean secure, String server, String path, String action) {
        this.server = server;
        this.path = path;
        this.action = action;
        this.secure = secure;
    }

    public HttpConnection(HttpConnection copy) {
        secure = copy.isSecure();
        server = copy.getServer();
        path = copy.getPath();
        action = copy.getAction();

    }

    @Override
    public String toString() {
        String stringValue = (isSecure() ? "https://" : "http://") + getServer() + "/" + getPath() + "/" + getAction();
        return stringValue;
    }
}
