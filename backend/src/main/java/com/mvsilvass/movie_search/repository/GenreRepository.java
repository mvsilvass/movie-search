package com.mvsilvass.movie_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvsilvass.movie_search.model.Genre;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
