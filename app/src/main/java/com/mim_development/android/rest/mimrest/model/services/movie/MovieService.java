package com.mim_development.android.rest.mimrest.model.services.movie;

import com.mim_development.android.rest.mimrest.model.services.base.service.Service;
import com.mim_development.android.rest.mimrest.model.services.base.service.callback.ServiceCallback;
import com.mim_development.android.rest.mimrest.model.services.movie.operations.GetMoviesOperation;
import com.mim_development.android.rest.mimrest.model.services.movie.requests.GetMoviesRequest;

import java.util.UUID;

public class MovieService extends Service {

    private static MovieService instance = new MovieService();

    public static MovieService getInstance() {
        return instance;
    }

    public UUID getMovies(
            String genre,
            String actor,
            ServiceCallback callback){

        GetMoviesOperation op = new GetMoviesOperation(
                new GetMoviesRequest(genre, actor),
                getOperationCallback());

        return invokeOperation(op, callback);

    }
}
