package com.mim_development.android.rest.mimrest.model.services.base.operation.callback;

import com.mim_development.android.rest.mimrest.model.services.base.operation.response.OperationErrorResponse;
import com.mim_development.android.rest.mimrest.model.services.base.operation.response.OperationSuccessResponse;

public interface OperationCallback {
    void success(OperationSuccessResponse response);
    void error(OperationErrorResponse response);
}
