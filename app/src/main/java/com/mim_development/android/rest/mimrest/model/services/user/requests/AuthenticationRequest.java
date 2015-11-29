package com.mim_development.android.rest.mimrest.model.services.user.requests;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.UUID;

public class AuthenticationRequest {

    private String userName;
    private String password;
    private UUID operationIdentity;

    public AuthenticationRequest(
            UUID operationIdentity,
            String userName,
            String password) {
        this.operationIdentity = operationIdentity;
        this.password = password;
        this.userName = userName;
    }

    @JsonIgnore
    public UUID getOperationIdentity() {
        return operationIdentity;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
