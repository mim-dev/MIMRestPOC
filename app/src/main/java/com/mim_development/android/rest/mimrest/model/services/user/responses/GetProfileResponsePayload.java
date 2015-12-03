package com.mim_development.android.rest.mimrest.model.services.user.responses;

import com.mim_development.android.rest.mimrest.model.services.base.definition.response.Payload;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GetProfileResponsePayload extends Payload {

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

    public GetProfileResponsePayload() {
    }

    public GetProfileResponsePayload(String firstName, String lastName, String countryCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryCode = countryCode;
    }
}
