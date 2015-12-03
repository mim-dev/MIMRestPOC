package com.mim_development.android.rest.mimrest.model.services.user.operations;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.definition.*;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationCallback;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationSuccessResponse;
import com.mim_development.android.rest.mimrest.model.services.base.operation.ServiceOperation;
import com.mim_development.android.rest.mimrest.model.services.user.requests.GetProfileRequest;
import com.mim_development.android.rest.mimrest.model.services.user.responses.GetProfileResponsePayload;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GetProfileOperation extends ServiceOperation {

    private static final String SERVICE_ACTION = "user/profile/%s";
    private String serviceAction;

    @Override
    protected Map<String, String> getRequestParameters() {
        return new HashMap<>();
    }

    @Override
    protected Globals.HttpVerbs getHttpVerb() {
        return Globals.HttpVerbs.GET;
    }

    @Override
    public String getServiceAction() {
        return serviceAction;
    }

    @Override
    protected OperationResultPayloadProcessor getOperationResultPayloadProcessor() {
        return new OperationResultPayloadProcessor() {
            @Override
            public OperationSuccessResponse processResponse(
                    UUID identifier, byte[] responsePayload) throws Exception {

                ObjectMapper mapper = new ObjectMapper();

                GetProfileResponsePayload getProfileResponsePayload = mapper.readValue(
                        responsePayload,
                        0,
                        responsePayload.length,
                        GetProfileResponsePayload.class);

                OperationSuccessResponse successResponse = new OperationSuccessResponse(
                        identifier,
                        GetProfileResponsePayload.class,
                        getProfileResponsePayload);

                return successResponse;
            }
        };
    }

    public GetProfileOperation(
            GetProfileRequest request,
            UUID identifier,
            OperationCallback callback){
        super(identifier, callback);
        serviceAction = String.format(SERVICE_ACTION, request.getProfileId());
    }
}
