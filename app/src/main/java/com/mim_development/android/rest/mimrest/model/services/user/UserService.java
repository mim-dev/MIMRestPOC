package com.mim_development.android.rest.mimrest.model.services.user;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.operation.BaseServiceOperation;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationCallback;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationErrorResponse;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationSuccessResponse;
import com.mim_development.android.rest.mimrest.model.services.base.operation.ServiceOperation;
import com.mim_development.android.rest.mimrest.model.services.base.service.ServiceCallback;
import com.mim_development.android.rest.mimrest.model.services.user.operations.AuthenticationOperation;
import com.mim_development.android.rest.mimrest.model.services.user.operations.GetProfileOperation;
import com.mim_development.android.rest.mimrest.model.services.user.requests.AuthenticationRequest;
import com.mim_development.android.rest.mimrest.model.services.user.requests.GetProfileRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    Map<UUID, OperationContainer> operations;

    private class OperationContainer {
        private BaseServiceOperation op;
        private ServiceCallback callback;

        public OperationContainer(BaseServiceOperation op, ServiceCallback callback) {
            this.op = op;
            this.callback = callback;
        }

        public BaseServiceOperation getOp() {
            return op;
        }

        public ServiceCallback getCallback() {
            return callback;
        }
    }

    private class OperationCallbackImpl implements OperationCallback {

        @Override
        public void success(OperationSuccessResponse response) {

            UUID identifier = response.getIdentifier();

            OperationContainer operationContainer = operations.get(identifier);

            if (operationContainer != null) {
                ServiceCallback callback = operationContainer.getCallback();
                callback.success(identifier, response);
            }
        }

        @Override
        public void error(OperationErrorResponse response) {

            UUID identifier = response.getIdentifier();

            OperationContainer operationContainer = operations.get(identifier);

            if (operationContainer != null) {
                operationContainer.callback.error(identifier, response);
            }
        }
    }

    private static UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    public UserService() {
        operations = new HashMap<>(10);
    }

    public void cancelOperation(UUID operationIdentifier) {

    }

    public UUID authenticate(
            String userName,
            String password,
            ServiceCallback callback) {

        UUID identifier = UUID.randomUUID();
        AuthenticationOperation op = new AuthenticationOperation(
                new AuthenticationRequest(userName, password),
                identifier,
                new OperationCallbackImpl());

        OperationContainer opContainer
                = new OperationContainer(op, callback);

        operations.put(identifier, opContainer);

        op.invoke();

        return identifier;
    }

    public UUID loadProfile(String profileId, ServiceCallback callback) {

        UUID identifier = UUID.randomUUID();

        GetProfileOperation op = new GetProfileOperation(
                new GetProfileRequest(profileId),
                identifier,
                new OperationCallbackImpl());

        OperationContainer opContainer
                = new OperationContainer(op, callback);

        operations.put(identifier, opContainer);

        op.invoke();

        return identifier;
    }
}
