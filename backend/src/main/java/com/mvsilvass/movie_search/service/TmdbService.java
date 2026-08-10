package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.client.TmdbClient;
import com.mvsilvass.movie_search.client.dto.TmdbPosterResponse;
import com.mvsilvass.movie_search.model.Movie;
import com.mvsilvass.movie_search.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.image.base-url}")
    private String imageBaseUrl;

    @Value("${tmdb.image.poster-size}")
    private String posterSize;

    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;

    public Movie fetchPosterIfMissing(Movie movie) {
        if (movie.getPosterPath() != null)
            return movie;

        try {
            TmdbPosterResponse response = tmdbClient.getMoviePosterById(movie.getTmdbId(), apiKey);

            if (response != null && response.posterPath() != null) {

                String path = response.posterPath().startsWith("/")
                        ? response.posterPath().substring(1)
                        : response.posterPath();

                String fullPosterUrl = imageBaseUrl + posterSize + "/" + path;
                movie.setPosterPath(fullPosterUrl);

                return movieRepository.save(movie);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar imagem para " + movie.getTitle() + ": " + e.getMessage());
        }

        return movie;
    }
}
