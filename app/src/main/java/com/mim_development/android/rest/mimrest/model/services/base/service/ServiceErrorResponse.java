package com.mim_development.android.rest.mimrest.model.services.base.service;

import com.mim_development.android.rest.mimrest.exception.OperationException;

public class ServiceErrorResponse {

    private OperationException exception;

    public OperationException getException() {
        return exception;
    }

    public ServiceErrorResponse(OperationException exception) {
        this.exception = exception;
    }
}
