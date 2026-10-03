package com.mvsilvass.movie_search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    private ResponseEntity<ErrorMessage> userNotFoundHandler(MovieNotFoundException ex){
        ErrorMessage error = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                Instant.now(),
                "filme não encontrado",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> genericExceptionHandler(Exception ex) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                Instant.now(),
                "Erro inesperado no servidor",
                "Ocorreu um problema ao processar sua requisição"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
