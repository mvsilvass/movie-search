package com.mvsilvass.movie_search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.service.MovieSearchService;
import com.mvsilvass.movie_search.dto.response.MovieSummary;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieSearchController {

    private final MovieSearchService movieSearchService;

    @GetMapping("/search")
    public List<MovieSummary> searchMovies(@RequestParam String query){
        return movieSearchService.searchBySimilarity(query);
    }
}
