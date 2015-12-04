package com.mim_development.android.rest.mimrest.model.services.base.http.executor;

import com.mim_development.android.rest.mimrest.model.services.base.http.response.HttpResponse;

public interface HttpExecutorMonitor {
    void result(HttpResponse response);
    void error(Throwable throwable);
}
