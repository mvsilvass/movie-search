package com.mvsilvass.movie_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mvsilvass.movie_search.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}