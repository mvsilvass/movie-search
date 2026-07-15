package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.model.Genre;
import com.mvsilvass.movie_search.model.Keyword;
import com.mvsilvass.movie_search.model.Movie;

import com.mvsilvass.movie_search.repository.MovieRepository;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieEmbeddingService {

    private final MovieRepository movieRepository;
    private final VectorStore vectorStore;

    public MovieEmbeddingService(MovieRepository movieRepository,
                                 @Qualifier("pgvectorstore") VectorStore vectorStore) {
        this.movieRepository = movieRepository;
        this.vectorStore = vectorStore;
    }

    public ResponseEntity<String> createEmbeddings(){
        List<Movie> movies = movieRepository.findAll();
        List<Document> documents = new ArrayList<>();
        for(Movie movie: movies){
            String genresJoined = String.join(", ", movie.getGenres().stream().map(Genre::getName).toList());
            String keywordsJoined = String.join(", ", movie.getKeywords().stream().map(Keyword::getName).toList());

            Map<String, Object> metadata = Map.of(
                    "movieId", movie.getMovieId(),
                    "title", movie.getTitle(),
                    "genres", genresJoined,
                    "keywords", keywordsJoined
            );

            Document doc = new Document(movie.getMovieId().toString(), movie.getOverview(), metadata);
            documents.add(doc);
        }

        vectorStore.add(documents);

        return ResponseEntity.ok("Successfully created embeddings for " + movies.size() + " movies!");

    }
}
