package com.mvsilvass.movie_search.controller;

import com.mvsilvass.movie_search.dto.response.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.service.MovieSearchService;
import com.mvsilvass.movie_search.dto.response.MovieSummary;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieSearchController {

    private final MovieSearchService movieSearchService;

    @GetMapping("/{tmdbId}")
    public ResponseEntity<MovieResponse> getMovieByTmdbId(@PathVariable Long tmdbId){
        return ResponseEntity.ok(movieSearchService.getMovieByTmdbId(tmdbId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieSummary>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieSearchService.searchBySimilarity(query));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Page<MovieSummary>> getTopRatedMovies(@PageableDefault(page = 0, size = 25, direction = Direction.DESC, sort = "voteAverage") Pageable pageable) {
        return ResponseEntity.ok(movieSearchService.getTopRatedMovies(pageable));
    }
}


