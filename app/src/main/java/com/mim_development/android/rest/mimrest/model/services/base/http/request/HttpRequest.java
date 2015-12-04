package com.mim_development.android.rest.mimrest.model.services.base.http.request;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.http.connection.HttpConnection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpConnection connection;
    private Map<String, String> headers;
    private int connectionTimeoutInMillis;
    private Globals.HttpVerbs verb;
    private Map<String, String> parameters;

    public HttpRequest(
            final HttpConnection connection,
            final Globals.HttpVerbs verb,
            final Map<String, String> headers,
            final int connectionTimeoutInMillis,
            final Map<String, String> parameters) {

        this.connection = connection;
        this.headers = headers;
        this.connectionTimeoutInMillis = connectionTimeoutInMillis;
        this.verb = verb;

        this.parameters = new HashMap<>(parameters.size());

        for (String key : parameters.keySet()) {
            String encodedKey;

            try {
                encodedKey = URLEncoder.encode(key, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encodedKey = key;
            }

            String encodedValue;
            String value = parameters.get(key);
            try {
                encodedValue = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encodedValue = value;
            }

            this.parameters.put(encodedKey, encodedValue);
        }
    }

    public String getConnectionString() {
        return connection.toString();
    }

    public Map<String, String> getHeaders() {
        Map<String, String> result = new HashMap<>(headers.size());
        result.putAll(headers);
        return result;
    }

    public int getConnectionTimeoutInMillis() {
        return connectionTimeoutInMillis;
    }

    public String getVerb() {
        return verb.name();
    }

    public Map<String, String> getParameters() {
        Map<String, String> parameterCopy = new HashMap<>(parameters.size());
        parameterCopy.putAll(parameters);
        return parameterCopy;
    }
}
