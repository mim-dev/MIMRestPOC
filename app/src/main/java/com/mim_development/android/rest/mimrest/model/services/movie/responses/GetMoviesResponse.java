package com.mim_development.android.rest.mimrest.model.services.movie.responses;

import com.mim_development.android.rest.mimrest.model.services.base.definition.response.Payload;

import java.util.List;

public class GetMoviesResponse extends Payload {

    private List<MovieEntity> movies;

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public GetMoviesResponse(List<MovieEntity> movies) {
        this.movies = movies;
    }
}
