package com.mvsilvass.movie_search.dto.csv;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCsvRecord {

    private Long budget;
    private String genres;
    private String homepage;
    private String id;
    private String keywords;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private Double popularity;
    private String productionCompanies;
    private String productionCountries;
    private String releaseDate;
    private Long revenue;
    private Double runtime;
    private String spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private Double voteAverage;
    private Integer voteCount;
}