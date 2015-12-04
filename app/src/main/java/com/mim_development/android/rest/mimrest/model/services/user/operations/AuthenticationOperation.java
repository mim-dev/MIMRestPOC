package com.mim_development.android.rest.mimrest.model.services.user.operations;

import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.definition.OperationResultPayloadProcessor;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationCallback;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationSuccessResponse;
import com.mim_development.android.rest.mimrest.model.services.base.operation.RequestPayloadServiceOperation;
import com.mim_development.android.rest.mimrest.model.services.user.requests.AuthenticationRequest;
import com.mim_development.android.rest.mimrest.model.services.user.responses.AuthenticationResponsePayload;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthenticationOperation extends RequestPayloadServiceOperation {

    private static final String SERVICE_ACTION = "user/authenticate";
    private AuthenticationRequest authenticationRequest;

    @Override
    protected Map<String, String> getRequestParameters() {
        return new HashMap<>();
    }

    @Override
    protected Globals.HttpVerbs getHttpVerb() {
        return Globals.HttpVerbs.POST;
    }

    @Override
    public String getServiceAction() {
        return SERVICE_ACTION;
    }

    @Override
    protected OperationResultPayloadProcessor getOperationResultPayloadProcessor() {
        return new OperationResultPayloadProcessor() {
            @Override
            public OperationSuccessResponse processResponse(
                    UUID identifier, byte[] responsePayload) throws Exception {

                ObjectMapper mapper = new ObjectMapper();
                AuthenticationResponsePayload authenticateResponsePayload = mapper.readValue(
                        responsePayload,
                        0,
                        responsePayload.length,
                        AuthenticationResponsePayload.class);

                OperationSuccessResponse successResponse = new OperationSuccessResponse(
                        identifier,
                        AuthenticationResponsePayload.class,
                        authenticateResponsePayload);

                return successResponse;
            }
        };
    }

    @Override
    protected byte[] getRequestPayload() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(authenticationRequest);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public AuthenticationOperation(
            AuthenticationRequest authenticationRequest,OperationCallback callback) {
        super(callback);
        this.authenticationRequest = authenticationRequest;
    }
}
