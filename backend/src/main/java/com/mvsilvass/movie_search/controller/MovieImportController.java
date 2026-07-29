package com.mvsilvass.movie_search.controller;

import com.mvsilvass.movie_search.service.MovieImportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class MovieImportController {

    private final MovieImportService movieImportService;

    @PostMapping("/imports/movies")
    public ResponseEntity<String> importMovies() {
        try {
            movieImportService.execute();

            return ResponseEntity.ok("Importação iniciada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao importar filmes: " + e.getMessage());
        }

    }
}