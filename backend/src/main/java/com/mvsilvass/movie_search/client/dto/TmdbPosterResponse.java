package com.mvsilvass.movie_search.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbPosterResponse(
        @JsonProperty("poster_path")
        String posterPath
){}