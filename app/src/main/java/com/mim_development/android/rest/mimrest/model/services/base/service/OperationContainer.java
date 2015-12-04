package com.mim_development.android.rest.mimrest.model.services.base.service;

import com.mim_development.android.rest.mimrest.model.services.base.operation.BaseServiceOperation;

public class OperationContainer {

    private BaseServiceOperation op;
    private ServiceCallback callback;

    public OperationContainer(BaseServiceOperation op, ServiceCallback callback) {
        this.op = op;
        this.callback = callback;
    }

    public BaseServiceOperation getOp() {
        return op;
    }

    public ServiceCallback getCallback() {
        return callback;
    }
}
