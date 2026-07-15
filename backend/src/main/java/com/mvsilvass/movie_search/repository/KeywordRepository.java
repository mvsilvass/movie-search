package com.mvsilvass.movie_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvsilvass.movie_search.model.Keyword;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
