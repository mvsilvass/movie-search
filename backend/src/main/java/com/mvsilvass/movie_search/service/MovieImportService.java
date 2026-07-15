package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.model.Genre;
import com.mvsilvass.movie_search.model.Keyword;
import com.mvsilvass.movie_search.model.Movie;

import com.mvsilvass.movie_search.repository.MovieRepository;
import com.mvsilvass.movie_search.csv.MovieRecordReader;
import com.mvsilvass.movie_search.csv.model.MovieRecord;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieImportService {

    @Value("${app.movie-csv-path}")
    private String filePath;

    private final MovieRecordReader movieRecordReader;
    private final MovieRepository movieRepository;

    public MovieImportService(MovieRecordReader movieRecordReader, MovieRepository movieRepository) {
        this.movieRecordReader = movieRecordReader;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public long importMoviesFromCsv(){

        if (this.movieRepository.count() > 0) return 0;

        List<MovieRecord> movieRecords = this.movieRecordReader.readAll(filePath);

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

        this.movieRepository.saveAll(movies);
        return movies.size();
    }
}

