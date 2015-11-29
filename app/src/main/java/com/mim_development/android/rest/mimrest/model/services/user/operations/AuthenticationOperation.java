package com.mim_development.android.rest.mimrest.model.services.user.operations;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.base.HttpConnection;
import com.mim_development.android.rest.mimrest.model.services.base.definition.*;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpPayloadExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpPayloadRequest;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpRequest;
import com.mim_development.android.rest.mimrest.model.services.user.AuthenticationOperationCallback;
import com.mim_development.android.rest.mimrest.model.services.user.requests.AuthenticationRequest;
import com.mim_development.android.rest.mimrest.model.services.user.responses.AuthenticationResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthenticationOperation {

    private static final String SERVICE_ACTION = "user/authenticate";

    private static final int AUTHENTICATION_CONNECTION_TIMEOUT_MILLIS = 8000;

    private class HttpMonitor implements HttpExecutorMonitor {

        @Override
        public void result(HttpResponse response) {
            if(callback != null){
                int status = response.getStatus();

                if(status >= 200 && status < 300){

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        AuthenticationResponse authenticationResponse = mapper.readValue(
                                response.getPayload(),
                                0,
                                response.getPayload().length,
                                AuthenticationResponse.class);
                        callback.authenticationSucceeded(operationIdentity, authenticationResponse);
                    } catch (IOException e) {
                        callback.authenticationError(
                                operationIdentity,
                                new OperationException("HTTP error code [" + response.getStatus() + "] received."));
                    }

                } else{
                    callback.authenticationError(
                            operationIdentity,
                            new OperationException("Failed to deserialize JSON response"));
                }

            }
        }

        @Override
        public void error(Throwable throwable) {
            if(callback != null){
                callback.authenticationError(
                        operationIdentity,
                        new OperationException("HTTP operation exception received.", throwable));
            }
        }
    }

    private HttpPayloadExecutor httpExecutor;

    private Thread executorThread;

    private AuthenticationOperationCallback callback;
    private UUID operationIdentity;

    public void invoke(
            AuthenticationRequest authenticationRequest,
            AuthenticationOperationCallback callback) throws IOException {

        this.callback = callback;
        operationIdentity = authenticationRequest.getOperationIdentity();
        final ObjectMapper mapper = new ObjectMapper();

        final HttpConnection connection = new HttpConnection(
                Globals.SERVICE_CONNECTION_IS_SECURE,
                Globals.SERVICE_CONNECTION_SERVER,
                Globals.SERVICE_CONNECTION_PATH,
                SERVICE_ACTION);

        HttpPayloadRequest httpRequest = new HttpPayloadRequest(
                connection,
                Globals.HttpVerbs.POST,
                buildRequestHeaders(),
                AUTHENTICATION_CONNECTION_TIMEOUT_MILLIS,
                new HttpMonitor(),
                mapper.writeValueAsBytes(authenticationRequest));

        httpExecutor = new HttpPayloadExecutor(httpRequest);
        executorThread = new Thread(httpExecutor);
        executorThread.start();
    }

    public Map<String, String> buildRequestHeaders(){
        Map<String, String> headerMap = new HashMap<>(2);
        headerMap.put("Content-Type", "Application/JSON");
        headerMap.put("Accept", "Application/JSON");
        return headerMap;
    }
}
