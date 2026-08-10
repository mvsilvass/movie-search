package com.mvsilvass.movie_search.client;

import com.mvsilvass.movie_search.client.dto.TmdbPosterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdbClient", url = "${tmdb.api.url}")
public interface TmdbClient {

    @GetMapping("/movie/{movie_id}")
    TmdbPosterResponse getMoviePosterById(
            @PathVariable("movie_id") Long id,
            @RequestParam("api_key") String apiKey
    );

}