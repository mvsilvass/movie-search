package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.model.Movie;

import com.mvsilvass.movie_search.repository.MovieRepository;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieSearchService {

    private final MovieRepository movieRepository;
    private final VectorStore vectorStore;

    public List<Movie> searchBySimilarity(String query) {
        List<Document> documents = vectorStore.similaritySearch(query);

        List<UUID> movieIds = documents.stream()
                .map(document -> UUID.fromString(document.getId()))
        .toList();

        return movieRepository.findAllById(movieIds);
    }
}
