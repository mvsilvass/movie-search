package com.mvsilvass.movie_search.dto.response;

import com.mvsilvass.movie_search.model.Genre;
import com.mvsilvass.movie_search.model.Keyword;
import com.mvsilvass.movie_search.model.Movie;

import java.util.function.Function;
import java.time.LocalDate;
import java.util.List;

public record MovieResponse(
        Long tmdbId,
        String title,
        String posterPath,
        String overview,
        Double popularity,
        LocalDate releaseDate,
        Double runtime,
        Double voteAverage,
        Integer voteCount,
        List<String> genres,
        List<String> keywords
) {
    public MovieResponse(Movie movie) {
        this(
                movie.getTmdbId(),
                movie.getTitle(),
                movie.getPosterPath(),
                movie.getOverview(),
                movie.getPopularity(),
                movie.getReleaseDate(),
                movie.getRuntime(),
                movie.getVoteAverage(),
                movie.getVoteCount(),
                mapToNames(movie.getGenres(), Genre::getName),
                mapToNames(movie.getKeywords(), Keyword::getName)
        );
    }

    private static <T> List<String> mapToNames(List<T> list, Function<T, String> nameExtractor) {
        if (list == null)
            return List.of();

        return list.stream()
                .map(nameExtractor)
        .toList();
    }
}