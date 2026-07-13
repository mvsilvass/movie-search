package com.mvsilvass.movie_search.config;

import com.mvsilvass.movie_search.repository.MovieRepository;

import com.mvsilvass.movie_search.csv.MovieRecordReader;
import com.mvsilvass.movie_search.csv.model.MovieRecord;

import com.mvsilvass.movie_search.model.Genre;
import com.mvsilvass.movie_search.model.Keyword;
import com.mvsilvass.movie_search.model.Movie;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieDataInitializer {

    @Value("${app.movie-csv-path}")
    private String filePath;

    private final MovieRecordReader movieRecordReader;
    private final MovieRepository movieRepository;

    public MovieDataInitializer(MovieRecordReader movieRecordReader, MovieRepository movieRepository) {
        this.movieRecordReader = movieRecordReader;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void run() {
        List<MovieRecord> movieRecords = movieRecordReader.readAll(filePath);

        List<Movie> movies = movieRecords.stream()
                .map(record -> Movie.builder()
                        .movieId(record.getMovieId())
                        .title(record.getTitle())
                        .overview(record.getOverview())
                        .releaseDate(record.getReleaseDate())
                        .runtime(record.getRuntime())
                        .popularity(record.getPopularity())
                        .voteAverage(record.getVoteAverage())
                        .voteCount(record.getVoteCount())
                        .genres(record.getGenres().stream()
                                .map(genreRecord -> new Genre(genreRecord.genreId(), genreRecord.name())).toList())
                        .keywords(record.getKeywords().stream()
                                .map(keywordRecord -> new Keyword(keywordRecord.keywordId(), keywordRecord.name())).toList())
                        .build()).toList();

        movieRepository.saveAll(movies);
    }
}
