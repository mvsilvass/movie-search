package com.mvsilvass.movie_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mvsilvass.movie_search.model.Movie;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
