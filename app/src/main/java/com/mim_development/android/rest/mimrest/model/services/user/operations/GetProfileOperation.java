package com.mim_development.android.rest.mimrest.model.services.user.operations;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.base.HttpConnection;
import com.mim_development.android.rest.mimrest.model.services.base.definition.*;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpRequest;
import com.mim_development.android.rest.mimrest.model.services.user.GetProfileOperationCallback;
import com.mim_development.android.rest.mimrest.model.services.user.requests.GetProfileRequest;
import com.mim_development.android.rest.mimrest.model.services.user.responses.GetProfileResponse;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GetProfileOperation {
    private static final String SERVICE_ACTION = "user/profile/%s";

    private static final int AUTHENTICATION_CONNECTION_TIMEOUT_MILLIS = 8000;

    private class HttpMonitor implements HttpExecutorMonitor {

        @Override
        public void result(HttpResponse response) {
            if(callback != null){
                int status = response.getStatus();

                if(status >= 200 && status < 300){

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        GetProfileResponse getProfileResponse = mapper.readValue(
                                response.getPayload(),
                                0,
                                response.getPayload().length,
                                GetProfileResponse.class);
                        callback.getProfileSucceeded(operationIdentity, getProfileResponse);
                    } catch (IOException e) {
                        callback.getProfileError(
                                operationIdentity,
                                new OperationException("HTTP error code [" + response.getStatus() + "] received."));
                    }

                } else{
                    callback.getProfileError(
                            operationIdentity,
                            new OperationException("Failed to deserialize JSON response"));
                }

            }
        }

        @Override
        public void error(Throwable throwable) {
            if(callback != null){
                callback.getProfileError(
                        operationIdentity,
                        new OperationException("HTTP operation exception received.", throwable));
            }
        }
    }

    private HttpExecutor httpExecutor;

    private Thread executorThread;

    private GetProfileOperationCallback callback;
    private UUID operationIdentity;

    public void invoke(
            GetProfileRequest getProfileRequest,
            GetProfileOperationCallback callback) throws IOException {

        this.callback = callback;
        operationIdentity = getProfileRequest.getOperationIdentity();

        final HttpConnection connection = new HttpConnection(
                Globals.SERVICE_CONNECTION_IS_SECURE,
                Globals.SERVICE_CONNECTION_SERVER,
                Globals.SERVICE_CONNECTION_PATH,
                String.format(SERVICE_ACTION, getProfileRequest.getProfileId()));

        HttpRequest httpRequest = new HttpRequest(
                connection,
                Globals.HttpVerbs.GET,
                buildRequestHeaders(),
                AUTHENTICATION_CONNECTION_TIMEOUT_MILLIS,
                new HttpMonitor(),
                new HashMap<String, String>());

        httpExecutor = new HttpExecutor(httpRequest);
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
