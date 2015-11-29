package com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor;

import android.util.Log;

import com.mim_development.android.rest.mimrest.model.services.base.definition.HttpExecutorMonitor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.HttpResponse;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.BaseHttpRequest;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpPayloadRequest;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpRequest;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpPayloadExecutor extends BaseHttpExecutor implements Runnable{

    private static final String TAG = HttpExecutor.class.getCanonicalName();

    private HttpPayloadRequest request;

    public HttpPayloadExecutor(final HttpPayloadRequest request) {

        this.request = request;
    }

    private String getConnectionString() {
        return request.getConnectionString();
    }

    private byte[] getPayload() {
        return request.getPayload();
    }

    @Override
    protected Map<String, String> getHeaders() {
        return request.getHeaders();
    }

    private int getConnectionTimeoutInMillis() {
        return request.getConnectionTimeoutInMillis();
    }

    private HttpExecutorMonitor getMonitor() {
        return request.getMonitor();
    }

    @Override
    public void run() {

        HttpURLConnection connection = null;
        HttpExecutorMonitor monitor = getMonitor();

        try {

            URL url = new URL(getConnectionString());

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(request.getVerb());
            addHeaders(connection);
            connection.setConnectTimeout(getConnectionTimeoutInMillis());

            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(getPayload());
            outputStream.close();

            int responseCode = connection.getResponseCode();

            byte[] responsePayload;

            // process the response
            if (responseCode > 199 && responseCode < 300) {
                InputStream inputStream = connection.getInputStream();
                responsePayload = IOUtils.toByteArray(inputStream);
                inputStream.close();
            } else {
                InputStream errorStream = connection.getErrorStream();
                responsePayload = IOUtils.toByteArray(errorStream);

                Log.e(TAG, "run(): received an HTTP error.  Status is:[" + responseCode
                        + "].  Payload is:[" + (new String(responsePayload)) + "]");

                errorStream.close();
            }

            connection.disconnect();

            if (monitor != null) {
                HttpResponse response = new HttpResponse(
                        connection.getHeaderFields(), responsePayload, responseCode);
                monitor.result(response);
            }
        } catch (IOException e) {
            if (monitor != null) {
                monitor.error(e);
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
