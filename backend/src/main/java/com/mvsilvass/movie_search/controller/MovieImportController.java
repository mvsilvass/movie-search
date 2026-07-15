package com.mvsilvass.movie_search.controller;

import com.mvsilvass.movie_search.service.MovieImportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieImportController {

    private final MovieImportService movieImportService;

    public MovieImportController(MovieImportService movieImportService) {
        this.movieImportService = movieImportService;
    }

    @GetMapping("/import")
    public ResponseEntity<String> importMovies(){
        long totalImported = movieImportService.importMoviesFromCsv();

        if (totalImported == 0) {
            return ResponseEntity.ok("Database already populated. Process skipped.");
        } else {
            return ResponseEntity.ok("Successfully imported " + totalImported + " movies!");
        }
    }
}
