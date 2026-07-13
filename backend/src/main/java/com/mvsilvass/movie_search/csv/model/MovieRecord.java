package com.mvsilvass.movie_search.csv.model;

import com.mvsilvass.movie_search.csv.converter.GenreRecordConverter;
import com.mvsilvass.movie_search.csv.converter.KeywordRecordConverter;

import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MovieRecord{
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

    @CsvCustomBindByName(column = "genres", converter = GenreRecordConverter.class)
    private List<GenreRecord> genres;

    @CsvCustomBindByName(column = "keywords", converter = KeywordRecordConverter.class)
    private List<KeywordRecord> keywords;
}
