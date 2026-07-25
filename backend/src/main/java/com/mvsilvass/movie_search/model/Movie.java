package com.mvsilvass.movie_search.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Movie(
        UUID id,
        String title,
        String overview,
        Double popularity,
        LocalDate releaseDate,
        Double runtime,
        Double voteAverage,
        Integer voteCount,
        List<Genre> genres,
        List<Keyword> keywords
) {}