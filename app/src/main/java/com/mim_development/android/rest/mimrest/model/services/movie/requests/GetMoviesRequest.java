package com.mim_development.android.rest.mimrest.model.services.movie.requests;

public class GetMoviesRequest {

    private String genre;
    private String actor;

    public String getGenre() {
        return genre;
    }

    public String getActor() {
        return actor;
    }

    public GetMoviesRequest(String genre, String actor) {
        this.genre = genre;
        this.actor = actor;
    }
}
