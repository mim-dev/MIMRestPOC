package com.mim_development.android.rest.mimrest.model.services.user.requests;


import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.UUID;

public class GetProfileRequest {
    private String profileId;
    private UUID operationIdentity;

    public GetProfileRequest(
            UUID operationIdentity,
            String profileId) {
        this.operationIdentity = operationIdentity;
        this.profileId = profileId;
    }

    @JsonIgnore
    public UUID getOperationIdentity() {
        return operationIdentity;
    }

    public String getProfileId() {
        return profileId;
    }

}
