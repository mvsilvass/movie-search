package com.mvsilvass.movie_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mvsilvass.movie_search.model.Movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Page<Movie> findAllByOrderByVoteCountDescVoteAverageDesc(Pageable pageable);
    Optional<Movie> findByTmdbId(Long tmdbId);
    boolean existsBy();
}
