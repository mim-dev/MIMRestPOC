package com.mim_development.android.rest.mimrest.model.services.base.operation;

public interface OperationCallback {
    void success(OperationSuccessResponse response);
    void error(OperationErrorResponse response);
}
