package com.mim_development.android.rest.mimrest.model.services.user.requests;

public class GetProfileRequest {
    private String profileId;

    public GetProfileRequest(
            String profileId) {
        this.profileId = profileId;
    }

    public String getProfileId() {
        return profileId;
    }
}
