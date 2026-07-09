package com.mvsilvass.movie_search.repository;

import com.mvsilvass.movie_search.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
