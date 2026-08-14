package com.mvsilvass.movie_search.batch;

import com.mvsilvass.movie_search.dto.csv.MovieCsvRecord;
import com.mvsilvass.movie_search.model.Movie;

import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovieProcessorTest {

    @InjectMocks
    private MovieProcessor movieProcessor;

    @Test
    @DisplayName("Should process movie successfully when CSV record contains valid data")
    void shouldProcessMovieSuccessfully() {
        // GIVEN

        MovieCsvRecord movieCsvRecord = MovieCsvRecord.builder()
                .id("100")
                .title("Test title")
                .overview("Test overview")
                .releaseDate("2024-01-15")
                .genres("[{\"id\": 1, \"name\": \"Test genre\"}]")
                .keywords("[{\"id\": 10, \"name\": \"Test keyword\"}]")
                .originalLanguage("en")
                .popularity(10.0)
                .runtime(120.0)
                .voteAverage(8.0)
                .voteCount(100)
        .build();

        // WHEN
        Movie processedMovie = movieProcessor.process(movieCsvRecord);

        // THEN
        assertNotNull(processedMovie);
        assertNotNull(processedMovie.getMovieId());
        assertEquals(100L, processedMovie.getTmdbId());
        assertEquals("Test title", processedMovie.getTitle());
        assertEquals("Test overview", processedMovie.getOverview());
        assertEquals(LocalDate.of(2024, 1, 15), processedMovie.getReleaseDate());

        assertNotNull(processedMovie.getGenres());
        assertEquals(1, processedMovie.getGenres().size());
        assertEquals("Test genre", processedMovie.getGenres().getFirst().getName());

        assertNotNull(processedMovie.getKeywords());
        assertEquals(1, processedMovie.getKeywords().size());
        assertEquals("Test keyword", processedMovie.getKeywords().getFirst().getName());
    }

    @Test
    @DisplayName("Should return empty list when json fields is null or empty")
    void shouldReturnEmptyListsWhenJsonFieldsAreNullOrEmpty() {
        // GIVEN
        MovieCsvRecord movieCsvRecord = MovieCsvRecord.builder()
                .id("100")
                .title("Test title")
                .overview("Test overview")
                .releaseDate("2024-01-15")
                .genres(null)
                .keywords("")
        .build();

        // WHEN
        Movie processedMovie = movieProcessor.process(movieCsvRecord);

        // THEN
        assertNotNull(processedMovie);
        assertNotNull(processedMovie.getGenres());
        assertTrue(processedMovie.getGenres().isEmpty());

        assertNotNull(processedMovie.getKeywords());
        assertTrue(processedMovie.getKeywords().isEmpty());
    }

    @Test
    @DisplayName("Should return null releaseDate when date string is null or blank")
    void shouldReturnNullReleaseDateWhenDateIsNullOnlyOrBlank() {
        MovieCsvRecord movieCsvRecord = MovieCsvRecord.builder()
                .id("100")
                .title("Test title")
                .releaseDate("   ")
                .build();

        // WHEN
        Movie processedMovie = movieProcessor.process(movieCsvRecord);

        // THEN
        assertNotNull(processedMovie);
        assertNull(processedMovie.getReleaseDate());
    }

    @Test
    @DisplayName("Should handle DateTimeParseException and return null when date format is invalid")
    void shouldReturnNullReleaseDateWhenDateFormatIsInvalid() {
        MovieCsvRecord movieCsvRecord = MovieCsvRecord.builder()
                .id("100")
                .title("Test title")
                .releaseDate("15/01/2024")
        .build();

        // WHEN
        Movie processedMovie = movieProcessor.process(movieCsvRecord);

        // THEN
        assertNotNull(processedMovie);
        assertNull(processedMovie.getReleaseDate());
    }
}