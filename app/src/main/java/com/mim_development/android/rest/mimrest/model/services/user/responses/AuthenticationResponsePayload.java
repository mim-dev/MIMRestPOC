package com.mim_development.android.rest.mimrest.model.services.user.responses;

import com.mim_development.android.rest.mimrest.model.services.base.Payload;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AuthenticationResponsePayload extends Payload {

    private String userId;
    private String firstName;
    private String lastName;

    public AuthenticationResponsePayload(){

    }

    public AuthenticationResponsePayload(String firstName, String lastName, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userId;
    }
}
