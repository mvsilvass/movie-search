package com.mvsilvass.movie_search.csv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeywordRecord(
    @JsonProperty("id")
    Long keywordId,

    @JsonProperty("name")
    String name
){}
