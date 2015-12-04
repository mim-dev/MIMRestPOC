package com.mim_development.android.rest.mimrest.model.services.base.operation;

import com.mim_development.android.rest.mimrest.model.services.base.definition.http.executor.HttpExecutor;
import com.mim_development.android.rest.mimrest.model.services.base.definition.http.request.HttpRequest;

public abstract class ServiceOperation extends BaseServiceOperation {

    protected ServiceOperation(OperationCallback callback){
        super(callback);
    }

    @Override
    public void invoke(){
        HttpRequest request = getHttpRequest();
        HttpExecutor executor = new HttpExecutor(request, getHttpExecutionMonitor());
        Thread executorThread = new Thread(executor);
        executorThread.start();
    }

    protected HttpRequest getHttpRequest(){
        return new HttpRequest(
                getConnection(),
                getHttpVerb(),
                buildRequestHeaders(),
                getConnectionTimeoutMillis(),
                getRequestParameters());
    }
}
