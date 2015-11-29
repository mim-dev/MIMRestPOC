package com.mim_development.android.rest.mimrest.model.services.user.responses;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GetProfileResponse {

    private String firstName;
    private String lastName;
    private String countryCode;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public GetProfileResponse() {
    }

    public GetProfileResponse(String firstName, String lastName, String countryCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
    }
}
