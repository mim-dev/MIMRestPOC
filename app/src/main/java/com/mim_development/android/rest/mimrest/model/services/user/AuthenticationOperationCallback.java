package com.mim_development.android.rest.mimrest.model.services.user;

import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.user.responses.AuthenticationResponse;

import java.util.UUID;

public interface AuthenticationOperationCallback {
    void authenticationSucceeded(UUID operationId, AuthenticationResponse response);
    void authenticationError(UUID operationId, OperationException exception);
}
