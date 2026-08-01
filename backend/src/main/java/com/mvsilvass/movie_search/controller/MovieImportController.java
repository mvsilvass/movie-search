package com.mvsilvass.movie_search.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.service.MovieImportService;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;

import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class MovieImportController {

    private final MovieImportService movieImportService;

    @PostMapping("/imports/movies")
    public ResponseEntity<String> importMovies() {
        try {
            JobExecution execution =  movieImportService.execute();

            if (execution.getStatus() == BatchStatus.FAILED) {

                String errorMessage = execution.getFailureExceptions().stream()
                        .findFirst()
                        .map(Throwable::getMessage)
                .orElse("Erro desconhecido durante o processamento do lote.");

                return ResponseEntity.internalServerError().body("Erro: " + errorMessage);
            }

            return ResponseEntity.ok("Importação concluída com sucesso!");

        } catch (Exception e) {
            String error = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();

            return ResponseEntity.internalServerError()
                    .body("Erro ao disparar a importação: " + error);
        }

    }

}