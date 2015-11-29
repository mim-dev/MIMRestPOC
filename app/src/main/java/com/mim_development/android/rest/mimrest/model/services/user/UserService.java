package com.mim_development.android.rest.mimrest.model.services.user;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.user.operations.AuthenticationOperation;
import com.mim_development.android.rest.mimrest.model.services.user.operations.GetProfileOperation;
import com.mim_development.android.rest.mimrest.model.services.user.requests.AuthenticationRequest;
import com.mim_development.android.rest.mimrest.model.services.user.requests.GetProfileRequest;
import com.mim_development.android.rest.mimrest.model.services.user.responses.AuthenticationResponse;
import com.mim_development.android.rest.mimrest.model.services.user.responses.GetProfileResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService implements AuthenticationOperationCallback, GetProfileOperationCallback {

    Map<UUID, GetProfileOperationContainer> getProfileOperations;
    Map<UUID, AuthenticationOperationContainer> authenticationOperations;

    @Override
    public void getProfileSucceeded(UUID operationId, GetProfileResponse response) {
        GetProfileOperationContainer getProfileOperationContainer = getProfileOperations.get(operationId);

        if(getProfileOperationContainer != null){
            authenticationOperations.remove(getProfileOperationContainer);
            GetProfileInvocationCallback callback = getProfileOperationContainer.getCallback();
            callback.success(operationId, response);
        } else{
            // TODO: implement a lock and release it here
        }
    }

    @Override
    public void getProfileError(UUID operationId, OperationException exception) {
        GetProfileOperationContainer getProfileOperationContainer = getProfileOperations.get(operationId);

        if(getProfileOperationContainer != null){
            authenticationOperations.remove(getProfileOperationContainer);
            GetProfileInvocationCallback callback = getProfileOperationContainer.getCallback();
            callback.error(operationId, exception);
        } else{
            // TODO: implement a lock and release it here
        }
    }

    @Override
    public void authenticationSucceeded(UUID operationId, AuthenticationResponse response) {

        AuthenticationOperationContainer authenticationOperationContainer = authenticationOperations.get(operationId);

        if(authenticationOperationContainer != null){
            authenticationOperations.remove(authenticationOperationContainer);
            AuthenticationInvocationCallback callback = authenticationOperationContainer.getCallback();
            callback.success(operationId, response);
        } else{
            // TODO: implement a lock and release it here
        }
    }

    @Override
    public void authenticationError(UUID operationId, OperationException exception) {
        AuthenticationOperationContainer authenticationOperationContainer = authenticationOperations.get(operationId);

        if(authenticationOperationContainer != null){
            authenticationOperations.remove(authenticationOperationContainer);
            AuthenticationInvocationCallback callback = authenticationOperationContainer.getCallback();
            callback.error(operationId, exception);
        } else{
            // TODO: implement a lock and release it here
        }
    }

    private class AuthenticationOperationContainer{

        private AuthenticationOperation op;
        private AuthenticationInvocationCallback callback;


        public AuthenticationOperationContainer(AuthenticationInvocationCallback callback, AuthenticationOperation op) {
            this.callback = callback;
            this.op = op;
        }

        public AuthenticationInvocationCallback getCallback() {
            return callback;
        }

        public AuthenticationOperation getOp() {
            return op;
        }
    }

    private class GetProfileOperationContainer{

        private GetProfileOperation op;
        private GetProfileInvocationCallback callback;


        public GetProfileOperationContainer(GetProfileInvocationCallback callback, GetProfileOperation op) {
            this.callback = callback;
            this.op = op;
        }

        public GetProfileInvocationCallback getCallback() {
            return callback;
        }

        public GetProfileOperation getOp() {
            return op;
        }
    }

    private static UserService instance = new UserService();

    public static UserService getInstance(){
        return instance;
    }

    public UserService() {

        authenticationOperations = new HashMap<>(5);
        getProfileOperations = new HashMap<>(5);
    }


    public void cancelOperation(UUID operationIdentifier){

    }

    public UUID authenticate(String userName, String password, AuthenticationInvocationCallback callback){

        UUID operationIdentifier;

        try {
            operationIdentifier = UUID.randomUUID();
            AuthenticationOperation op = new AuthenticationOperation();
            AuthenticationOperationContainer opContainer = new AuthenticationOperationContainer(callback, op);
            op.invoke(new AuthenticationRequest(operationIdentifier, userName, password), this);
            authenticationOperations.put(operationIdentifier, opContainer);
        } catch (IOException e) {
            operationIdentifier = Globals.EMPTY_UUID;
        }

        return operationIdentifier;

    }

    public UUID loadProfile(String profileId, GetProfileInvocationCallback callback){
        UUID operationIdentifier;

        try {
            operationIdentifier = UUID.randomUUID();
            GetProfileOperation op = new GetProfileOperation();
            GetProfileOperationContainer opContainer = new GetProfileOperationContainer(callback, op);
            op.invoke(new GetProfileRequest(operationIdentifier, profileId), this);
            getProfileOperations.put(operationIdentifier, opContainer);
        } catch (IOException e) {
            operationIdentifier = Globals.EMPTY_UUID;
        }

        return operationIdentifier;
    }
}
