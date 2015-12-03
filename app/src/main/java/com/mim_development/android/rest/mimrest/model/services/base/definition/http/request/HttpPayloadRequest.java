package com.mim_development.android.rest.mimrest.model.services.base.definition.http.request;


import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.HttpConnection;
import com.mim_development.android.rest.mimrest.model.services.base.definition.HttpExecutorMonitor;

import java.util.Arrays;
import java.util.Map;

public class HttpPayloadRequest extends HttpRequest {

    private byte[] payload;

    public HttpPayloadRequest(
            final HttpConnection connection,
            Globals.HttpVerbs verb,
            final Map<String, String> headers,
            final int connectionTimeoutInMillis,
            final HttpExecutorMonitor monitor,
            final Map<String, String> parameters,
            final byte[] payload) {

        super(connection, verb, headers, connectionTimeoutInMillis, monitor, parameters);
        this.payload = Arrays.copyOf(payload, payload.length);
    }

    public byte[] getPayload() {
        byte[] result = Arrays.copyOf(payload, payload.length);
        return result;
    }
}
