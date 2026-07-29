package com.mvsilvass.movie_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mvsilvass.movie_search.model.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}