package com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor;

import java.net.HttpURLConnection;
import java.util.Map;

public abstract class BaseHttpExecutor {

    protected abstract Map<String, String> getHeaders();

    protected BaseHttpExecutor() {

    }

    protected void addHeaders(HttpURLConnection connection) {
        Map<String, String> headers = getHeaders();

        if(headers != null) {
            for (String headerName : headers.keySet()) {
                connection.setRequestProperty(headerName, headers.get(headerName));
            }
        }
    }
}
