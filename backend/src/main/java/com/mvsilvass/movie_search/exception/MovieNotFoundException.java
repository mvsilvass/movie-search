package com.mvsilvass.movie_search.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String message) {
        super(message);
    }

}
