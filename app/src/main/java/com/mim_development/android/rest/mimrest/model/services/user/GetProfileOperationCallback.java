package com.mim_development.android.rest.mimrest.model.services.user;

import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.user.responses.GetProfileResponse;

import java.util.UUID;

public interface GetProfileOperationCallback {
    void getProfileSucceeded(UUID operationId, GetProfileResponse response);
    void getProfileError(UUID operationId, OperationException exception);
}

