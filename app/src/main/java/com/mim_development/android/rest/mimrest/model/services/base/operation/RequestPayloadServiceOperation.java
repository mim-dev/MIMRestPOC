package com.mim_development.android.rest.mimrest.model.services.base.operation;

import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpPayloadExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpPayloadRequest;

import java.util.UUID;

public abstract class RequestPayloadServiceOperation extends BaseServiceOperation {

    protected RequestPayloadServiceOperation(UUID identifier, OperationCallback callback){
        super(identifier, callback);
    }

    public void invoke(){
        HttpPayloadRequest request = getHttpPayloadRequest();
        HttpPayloadExecutor executor = new HttpPayloadExecutor(request);
        Thread executorThread = new Thread(executor);
        executorThread.start();
    }

    protected HttpPayloadRequest getHttpPayloadRequest(){
        return new HttpPayloadRequest(getConnection(),
                getHttpVerb(),
                buildRequestHeaders(),
                getConnectionTimeoutMillis(),
                getHttpExecutionMonitor(),
                getRequestParameters(),
                getPayload());
    }

    protected abstract byte[] getPayload();
}
