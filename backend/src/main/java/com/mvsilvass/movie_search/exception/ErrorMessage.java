package com.mvsilvass.movie_search.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private HttpStatus status;
    private Instant timestamp;
    private String error;
    private String message;
}