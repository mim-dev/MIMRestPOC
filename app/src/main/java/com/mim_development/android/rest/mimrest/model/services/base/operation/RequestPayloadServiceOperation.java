package com.mim_development.android.rest.mimrest.model.services.base.operation;

import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpPayloadExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpPayloadRequest;

public abstract class RequestPayloadServiceOperation extends BaseServiceOperation {

    protected RequestPayloadServiceOperation(OperationCallback callback){
        super(callback);
    }

    @Override
    public void invoke(){
        HttpPayloadRequest request = getHttpPayloadRequest();
        HttpPayloadExecutor executor = new HttpPayloadExecutor(request, getHttpExecutionMonitor());
        Thread executorThread = new Thread(executor);
        executorThread.start();
    }

    protected HttpPayloadRequest getHttpPayloadRequest(){
        return new HttpPayloadRequest(getConnection(),
                getHttpVerb(),
                buildRequestHeaders(),
                getConnectionTimeoutMillis(),
                getRequestParameters(),
                getRequestPayload());
    }

    protected abstract byte[] getRequestPayload();
}
