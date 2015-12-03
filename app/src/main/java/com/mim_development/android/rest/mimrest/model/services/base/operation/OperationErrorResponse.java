package com.mim_development.android.rest.mimrest.model.services.base.operation;

import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.base.service.ServiceErrorResponse;

import java.util.UUID;

public class OperationErrorResponse extends ServiceErrorResponse {

    private UUID identifier;

    public UUID getIdentifier() {
        return identifier;
    }

    public OperationErrorResponse(UUID identifier, OperationException operationException) {
        super(operationException);
        this.identifier = identifier;
    }
}
