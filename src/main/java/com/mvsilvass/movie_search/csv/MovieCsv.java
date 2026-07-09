package com.mvsilvass.movie_search.csv;

import com.mvsilvass.movie_search.csv.converter.GenreCsvConverter;

import com.mvsilvass.movie_search.csv.converter.JsonToGenreSetConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
public class MovieCsv {

    @CsvBindByName(column = "id")
    private Long movieId;

    @CsvBindByName(column = "original_title")
    private String title;

    @CsvBindByName(column = "overview")
    private String overview;

    @CsvBindByName(column = "popularity")
    private Double popularity;

    @CsvDate("yyyy-MM-dd")
    @CsvBindByName(column = "release_date")
    private LocalDate releaseDate;

    @CsvBindByName(column = "runtime")
    private Double runtime;

    @CsvBindByName(column = "vote_average")
    private Double voteAverage;

    @CsvBindByName(column = "vote_count")
    private Integer voteCount;

    @CsvCustomBindByName(column = "genres", converter = JsonToGenreSetConverter.class)
    private Set<GenreCsv> genres;

    @CsvBindByName(column = "keywords")
    private Set<KeywordCsv> keywords;

}
