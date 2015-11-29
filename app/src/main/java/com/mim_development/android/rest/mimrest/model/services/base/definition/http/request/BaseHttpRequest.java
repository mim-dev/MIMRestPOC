package com.mim_development.android.rest.mimrest.model.services.base.definition.http.request;


import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.HttpConnection;
import com.mim_development.android.rest.mimrest.model.services.base.definition.HttpExecutorMonitor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseHttpRequest {

    private HttpConnection connection;
    private HttpExecutorMonitor monitor;
    private Map<String, String> headers;
    private int connectionTimeoutInMillis;
    private Globals.HttpVerbs verb;

    public BaseHttpRequest(
            final HttpConnection connection,
            final Globals.HttpVerbs verb,
            final Map<String, String> headers,
            final int connectionTimeoutInMillis,
            final HttpExecutorMonitor monitor) {
        this.connection = new HttpConnection(connection);

        this.headers = new HashMap<>(headers.size());
        this.headers.putAll(headers);

        this.connectionTimeoutInMillis = connectionTimeoutInMillis;
        this.monitor = monitor;

        this.verb = verb;
    }

    public String getConnectionString() {
        return connection.toString();
    }

    public HttpExecutorMonitor getMonitor() {
        return monitor;
    }

    public Map<String, String> getHeaders() {
        Map<String, String> result = new HashMap<>(headers.size());
        result.putAll(headers);
        return result;
    }

    public int getConnectionTimeoutInMillis() {
        return connectionTimeoutInMillis;
    }

    public String getVerb(){return verb.name();}

}
