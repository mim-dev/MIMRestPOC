package com.mim_development.android.rest.mimrest.model.services.user;

import com.mim_development.android.rest.mimrest.model.services.base.service.Service;
import com.mim_development.android.rest.mimrest.model.services.base.service.callback.ServiceCallback;
import com.mim_development.android.rest.mimrest.model.services.user.operations.AuthenticationOperation;
import com.mim_development.android.rest.mimrest.model.services.user.operations.GetProfileOperation;
import com.mim_development.android.rest.mimrest.model.services.user.requests.AuthenticationRequest;
import com.mim_development.android.rest.mimrest.model.services.user.requests.GetProfileRequest;

import java.util.UUID;

public class UserService extends Service {

    private static UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    public UUID authenticate(
            String userName,
            String password,
            ServiceCallback callback) {

        AuthenticationOperation op = new AuthenticationOperation(
                new AuthenticationRequest(userName, password),
                getOperationCallback());

        return invokeOperation(op, callback);
    }

    public UUID loadProfile(String profileId, ServiceCallback callback) {

        GetProfileOperation op = new GetProfileOperation(
                new GetProfileRequest(profileId),
                getOperationCallback());

        return invokeOperation(op, callback);
    }
}
