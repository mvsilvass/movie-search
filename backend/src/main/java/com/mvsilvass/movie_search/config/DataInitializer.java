package com.mvsilvass.movie_search.config;

import com.mvsilvass.movie_search.service.MovieImportService;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MovieImportService movieImportService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {

        if (movieImportService.isDatabasePopulated()) {
            log.info("Dados já existentes no banco. O processo de importação foi ignorado.");
            return;
        }

        log.info("Aplicação pronta! Iniciando verificação e carga inicial de filmes...");

        try {
            JobExecution execution =  movieImportService.execute();

            if (execution.getStatus() == BatchStatus.FAILED) {

                String errorMessage = execution.getFailureExceptions().stream()
                        .findFirst()
                        .map(Throwable::getMessage)
                        .orElse("Erro desconhecido durante o processamento do lote.");

                log.error("Erro: {}", errorMessage);
                return;
            }

            log.info("Importação concluída com sucesso!");

        } catch (Exception e) {
            String error = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            log.error("Erro ao disparar a importação: {}", error);
        }
    }
}