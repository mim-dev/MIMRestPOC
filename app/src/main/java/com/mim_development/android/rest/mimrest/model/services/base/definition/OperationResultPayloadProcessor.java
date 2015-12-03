package com.mim_development.android.rest.mimrest.model.services.base.definition;

import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationSuccessResponse;

import java.util.UUID;

public interface OperationResultPayloadProcessor {
    OperationSuccessResponse processResponse(
            UUID identifier, byte[] responsePayload) throws Exception;
}
