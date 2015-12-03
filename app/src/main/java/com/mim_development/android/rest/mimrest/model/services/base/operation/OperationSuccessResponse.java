package com.mim_development.android.rest.mimrest.model.services.base.operation;

import com.mim_development.android.rest.mimrest.model.services.base.definition.response.Payload;
import com.mim_development.android.rest.mimrest.model.services.base.service.ServiceSuccessResponse;

import java.util.UUID;

public class OperationSuccessResponse extends ServiceSuccessResponse {

    private UUID identifier;

    public UUID getIdentifier() {
        return identifier;
    }

    public <T extends Payload> OperationSuccessResponse(
            UUID identifier, Class<T> type, T instance) {
        super(type, instance);
        this.identifier = identifier;
    }
}
