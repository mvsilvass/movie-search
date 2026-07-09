package com.mvsilvass.movie_search.repository;

import com.mvsilvass.movie_search.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
