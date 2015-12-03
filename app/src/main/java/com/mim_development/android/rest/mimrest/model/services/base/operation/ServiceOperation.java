package com.mim_development.android.rest.mimrest.model.services.base.operation;

import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpRequest;

import java.util.UUID;

public abstract class ServiceOperation extends BaseServiceOperation {

    protected ServiceOperation(UUID identifier, OperationCallback callback){
        super(identifier, callback);
    }

    public void invoke(){
        HttpRequest request = getHttpRequest();
        HttpExecutor executor = new HttpExecutor(request);
        Thread executorThread = new Thread(executor);
        executorThread.start();
    }

    protected HttpRequest getHttpRequest(){
        return new HttpRequest(
                getConnection(),
                getHttpVerb(),
                buildRequestHeaders(),
                getConnectionTimeoutMillis(),
                getHttpExecutionMonitor(),
                getRequestParameters());
    }
}
