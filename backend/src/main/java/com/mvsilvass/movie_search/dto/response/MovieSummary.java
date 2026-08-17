package com.mvsilvass.movie_search.dto.response;

import com.mvsilvass.movie_search.model.Movie;

public record MovieSummary(
        Long tmdbId,
        String title,
        String posterPath
) {
    public MovieSummary(Movie movie) {
        this(movie.getTmdbId(), movie.getTitle(), movie.getPosterPath());
    }
}