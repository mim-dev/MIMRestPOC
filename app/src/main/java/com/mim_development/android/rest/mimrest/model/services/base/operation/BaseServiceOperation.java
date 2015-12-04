package com.mim_development.android.rest.mimrest.model.services.base.operation;


import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.base.HttpConnection;
import com.mim_development.android.rest.mimrest.model.services.base.definition.HttpExecutorMonitor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.HttpResponse;
import com.mim_development.android.rest.mimrest.model.services.base.definition.OperationResultPayloadProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseServiceOperation {

    private class HttpExecutorMonitorImpl implements HttpExecutorMonitor {

        private OperationResultPayloadProcessor payloadProcessor;

        public HttpExecutorMonitorImpl(
                OperationResultPayloadProcessor payloadProcessor){
            this.payloadProcessor = payloadProcessor;
        }

        @Override
        public void result(HttpResponse response) {
            if(callback != null){

                UUID operationIdentifier = getIdentifier();
                int status = response.getStatus();

                if(status >= 200 && status < 300){

                    try {
                        OperationSuccessResponse successResponse = payloadProcessor.processResponse(
                                operationIdentifier, response.getPayload());
                        callback.success(successResponse);
                    } catch (Exception e) {
                        callback.error(new OperationErrorResponse(
                                operationIdentifier,
                                new OperationException("Failed to process service response")));
                    }
                } else{
                        callback.error(new OperationErrorResponse(
                                operationIdentifier,
                                new OperationException("HTTP error code [" + response.getStatus() + "] received.")));
                    }
            }
        }

        @Override
        public void error(Throwable throwable) {
            if(callback != null){
                callback.error(new OperationErrorResponse(
                        getIdentifier(),
                        new OperationException("HTTP operation exception received.", throwable)));
            }
        }
    }

    protected OperationCallback callback;
    private UUID identifier;

    abstract protected String getServiceAction();
    abstract protected Globals.HttpVerbs getHttpVerb();
    abstract protected Map<String, String> getRequestParameters();
    abstract protected OperationResultPayloadProcessor getOperationResultPayloadProcessor();

    abstract public void invoke();

    public UUID getIdentifier(){
        return identifier;
    }

    protected HttpExecutorMonitor getHttpExecutionMonitor(){
        return new HttpExecutorMonitorImpl(getOperationResultPayloadProcessor());
    }

    protected BaseServiceOperation(OperationCallback callback){
        this.callback = callback;
        identifier = UUID.randomUUID();
    }

    protected HttpConnection getConnection(){
        return new HttpConnection(
                Globals.SERVICE_CONNECTION_IS_SECURE,
                Globals.SERVICE_CONNECTION_SERVER,
                Globals.SERVICE_CONNECTION_PATH,
                getServiceAction());
    }

    protected int getConnectionTimeoutMillis(){
        return Globals.DEFAULT_CONNECTION_TIMEOUT_MILLIS;
    }

    protected Map<String, String> buildRequestHeaders(){
        Map<String, String> headerMap = new HashMap<>(2);
        headerMap.put("Content-Type", "Application/JSON");
        headerMap.put("Accept", "Application/JSON");
        return headerMap;
    }

    public void cancel(){
        ;       // do nothing for now
    }
}
