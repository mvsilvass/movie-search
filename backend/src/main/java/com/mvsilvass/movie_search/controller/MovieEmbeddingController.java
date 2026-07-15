package com.mvsilvass.movie_search.controller;

import com.mvsilvass.movie_search.service.MovieEmbeddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies/embeddings")
public class MovieEmbeddingController {

    private final MovieEmbeddingService movieEmbeddingService;

    public MovieEmbeddingController(MovieEmbeddingService movieEmbeddingService) {
        this.movieEmbeddingService = movieEmbeddingService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> createEmbeddings(){
        return this.movieEmbeddingService.createEmbeddings();
    }
}
