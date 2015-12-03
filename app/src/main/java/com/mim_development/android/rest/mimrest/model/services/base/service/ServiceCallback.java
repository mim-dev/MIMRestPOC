package com.mim_development.android.rest.mimrest.model.services.base.service;

import java.util.UUID;

public interface ServiceCallback {
    void success(UUID operationId, ServiceSuccessResponse response);
    void error(UUID operationId, ServiceErrorResponse response);
}
