package com.mvsilvass.movie_search.csv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenreRecord(
    @JsonProperty("id")
    Long genreId,

    @JsonProperty("name")
    String name
){



}