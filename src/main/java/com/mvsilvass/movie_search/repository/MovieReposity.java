package com.mvsilvass.movie_search.repository;

import com.mvsilvass.movie_search.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReposity extends JpaRepository<Movie, Long> {
}
