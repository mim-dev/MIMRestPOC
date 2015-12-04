package com.mim_development.android.rest.mimrest.model.services.movie.operations;


import com.mim_development.android.rest.mimrest.Globals;
import com.mim_development.android.rest.mimrest.model.services.base.definition.OperationResultPayloadProcessor;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationCallback;
import com.mim_development.android.rest.mimrest.model.services.base.operation.OperationSuccessResponse;
import com.mim_development.android.rest.mimrest.model.services.base.operation.ServiceOperation;
import com.mim_development.android.rest.mimrest.model.services.movie.requests.GetMoviesRequest;
import com.mim_development.android.rest.mimrest.model.services.movie.responses.GetMoviesResponse;
import com.mim_development.android.rest.mimrest.model.services.movie.responses.MovieEntity;
import com.mim_development.android.rest.mimrest.model.services.user.responses.GetProfileResponsePayload;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GetMoviesOperation extends ServiceOperation {

    private static final String SERVICE_ACTION = "movie/movies";

    private static final String ACTOR_PARAMETER_NAME = "actor";
    private static final String GENRE_PARAMETER_NAME = "genre";

    private Map<String, String> unEncodedParameters;

    @Override
    protected Map<String, String> getRequestParameters() {
        return unEncodedParameters;
    }

    @Override
    protected Globals.HttpVerbs getHttpVerb() {
        return Globals.HttpVerbs.GET;
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

                String resultText = new String(responsePayload);

                MovieEntity[] getMoviesResponsePayload = mapper.readValue(
                        responsePayload,
                        0,
                        responsePayload.length,
                        MovieEntity[].class);

                OperationSuccessResponse successResponse = new OperationSuccessResponse(
                        identifier,
                        GetMoviesResponse.class,
                        new GetMoviesResponse(Arrays.asList(getMoviesResponsePayload)));

                return successResponse;
            }
        };
    }

    public GetMoviesOperation(
            GetMoviesRequest request,
            OperationCallback callback){
        super(callback);
        unEncodedParameters = new HashMap<>(2);
        unEncodedParameters.put(ACTOR_PARAMETER_NAME, request.getActor());
        unEncodedParameters.put(GENRE_PARAMETER_NAME, request.getGenre());
    }
}
