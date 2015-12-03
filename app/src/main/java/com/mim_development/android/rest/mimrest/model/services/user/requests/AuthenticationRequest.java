package com.mim_development.android.rest.mimrest.model.services.user.requests;

public class AuthenticationRequest {

    private String userName;
    private String password;

    public AuthenticationRequest(
            String userName,
            String password) {
        this.password = password;
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
