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
        private UUID operationIdentity;

        public HttpExecutorMonitorImpl(
                UUID operationIdentity,
                OperationResultPayloadProcessor payloadProcessor){
            this.operationIdentity = operationIdentity;
            this.payloadProcessor = payloadProcessor;
        }

        @Override
        public void result(HttpResponse response) {
            if(callback != null){
                int status = response.getStatus();

                if(status >= 200 && status < 300){

                    try {
                        OperationSuccessResponse successResponse = payloadProcessor.processResponse(
                                operationIdentity, response.getPayload());
                        callback.success(successResponse);
                    } catch (Exception e) {
                        callback.error(new OperationErrorResponse(
                                operationIdentity,
                                new OperationException("Failed to process service response")));
                    }
                } else{
                        callback.error(new OperationErrorResponse(
                                operationIdentity,
                                new OperationException("HTTP error code [" + response.getStatus() + "] received.")));
                    }
            }
        }

        @Override
        public void error(Throwable throwable) {
            if(callback != null){
                callback.error(new OperationErrorResponse(
                        operationIdentity,
                        new OperationException("HTTP operation exception received.", throwable)));
            }
        }
    }

    protected OperationCallback callback;
    protected UUID identifier;

    abstract protected String getServiceAction();
    abstract protected Globals.HttpVerbs getHttpVerb();
    abstract protected Map<String, String> getRequestParameters();
    abstract protected OperationResultPayloadProcessor getOperationResultPayloadProcessor();

    protected HttpExecutorMonitor getHttpExecutionMonitor(){
        return new HttpExecutorMonitorImpl(identifier, getOperationResultPayloadProcessor());
    }

    protected BaseServiceOperation(UUID identifier, OperationCallback callback){
        this.identifier = identifier;
        this.callback = callback;
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
