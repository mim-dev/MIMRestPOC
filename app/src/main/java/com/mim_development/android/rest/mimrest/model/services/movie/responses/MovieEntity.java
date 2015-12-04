package com.mim_development.android.rest.mimrest.model.services.movie.responses;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MovieEntity {

    private String genre;
    private String title;

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public MovieEntity() {
    }

    public MovieEntity(String genre, String title) {
        this.genre = genre;
        this.title = title;
    }
}
