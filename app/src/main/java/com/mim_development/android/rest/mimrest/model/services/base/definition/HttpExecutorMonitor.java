package com.mim_development.android.rest.mimrest.model.services.base.definition;

public interface HttpExecutorMonitor {
    void result(HttpResponse response);
    void error(Throwable throwable);
}
