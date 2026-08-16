package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.client.TmdbClient;
import com.mvsilvass.movie_search.client.dto.TmdbPosterResponse;
import com.mvsilvass.movie_search.model.Movie;
import com.mvsilvass.movie_search.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TmdbServiceTest {

    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private final String API_KEY = "test-api-key";
    private final String POSTER_SIZE = "w500";

    @Mock
    private TmdbClient tmdbClient;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private TmdbService tmdbService;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tmdbService, "IMAGE_BASE_URL", IMAGE_BASE_URL);
        ReflectionTestUtils.setField(tmdbService, "POSTER_SIZE", POSTER_SIZE);
        ReflectionTestUtils.setField(tmdbService, "API_KEY", API_KEY);
    }

    @Test
    @DisplayName("Should fetch poster and save on repository when it is missing")
    void shouldFetchPosterAndSaveMovieWhenMissing() {
        // GIVEN
        Long movieId = 101L;
        Movie movie = Movie.builder()
                .tmdbId(movieId).build();

        String posterPath = "/poster123.jpg";
        TmdbPosterResponse response = new TmdbPosterResponse(posterPath);

        when(tmdbClient.getMoviePosterById(movieId, API_KEY)).thenReturn(response);
        when(movieRepository.save(any(Movie.class))).thenAnswer(returnsFirstArg());

        // WHEN
        Movie result = tmdbService.fetchPosterIfMissing(movie);

        // THEN
        assertNotNull(result);
        assertEquals(IMAGE_BASE_URL + POSTER_SIZE + posterPath, result.getPosterPath());

        verify(tmdbClient, times(1)).getMoviePosterById(movieId, API_KEY);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    @DisplayName("Should return movie without calling API when poster path already exists")
    void shouldReturnMovieImmediatelyWhenPosterAlreadyExists() {
        // GIVEN
        Long movieId = 101L;
        String posterPath = IMAGE_BASE_URL + POSTER_SIZE + "existing.jpg";
        Movie movie = Movie.builder()
                .tmdbId(movieId)
                .posterPath(posterPath)
        .build();

        // WHEN
        Movie result = tmdbService.fetchPosterIfMissing(movie);

        // THEN
        assertNotNull(result);
        assertEquals(posterPath, result.getPosterPath());

        verifyNoInteractions(tmdbClient);
        verifyNoInteractions(movieRepository);
    }

    @Test
    @DisplayName("Should throw RuntimeException when TMDB client fails")
    void shouldThrowRuntimeExceptionWhenClientFails() {
        // GIVEN
        Long movieId = 101L;
        Movie movie = Movie.builder().tmdbId(movieId).build();

        when(tmdbClient.getMoviePosterById(movieId, API_KEY))
                .thenThrow(new RuntimeException("API error"));

        // WHEN & THEN
        assertThrows(
                RuntimeException.class,
                () -> tmdbService.fetchPosterIfMissing(movie)
        );

        verify(tmdbClient, times(1)).getMoviePosterById(movieId, API_KEY);
        verifyNoInteractions(movieRepository);
    }
}