package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.dto.response.MovieResponse;
import com.mvsilvass.movie_search.dto.response.MovieSummary;
import com.mvsilvass.movie_search.exception.MovieNotFoundException;
import com.mvsilvass.movie_search.model.Movie;

import com.mvsilvass.movie_search.repository.MovieRepository;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieSearchService {

    private final MovieRepository movieRepository;
    private final VectorStore vectorStore;
    private final TmdbService tmdbService;

    public List<MovieSummary> searchBySimilarity(String query) {
        int DEFAULT_TOPK = 25;

        SearchRequest searchRequest = SearchRequest.builder()
                .topK(DEFAULT_TOPK)
                .query(query)
        .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        List<UUID> movieIds = documents.stream()
                .map(document -> UUID.fromString(document.getId()))
        .toList();

        List<Movie> movies = movieRepository.findAllById(movieIds);

        return movies.stream()
                .map(tmdbService::fetchPosterIfMissing)
                .map(MovieSummary::new)
        .toList();
    }

    public MovieResponse getMovieByTmdbId(Long tmdbId) {
        Movie movie = movieRepository.findByTmdbId(tmdbId)
                .orElseThrow(() -> new MovieNotFoundException("Filme com ID_TMDB "+ tmdbId + " não encontrado"));

        return new MovieResponse(movie);
    }

    public Page<MovieSummary> getTopRatedMovies(Pageable pageable){
        Page<Movie> movies = movieRepository.findAllByOrderByVoteCountDescVoteAverageDesc(pageable);

        return movies.map(
                movie -> new MovieSummary(tmdbService.fetchPosterIfMissing(movie))
        );
    }
}
