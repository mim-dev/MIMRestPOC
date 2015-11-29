package com.mim_development.android.rest.mimrest.model.services.user;

import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.user.responses.AuthenticationResponse;

import java.util.UUID;

public interface AuthenticationInvocationCallback {

    void success(UUID operationIdentity, AuthenticationResponse response);
    void error(UUID operationIdentity, OperationException exception);
}
