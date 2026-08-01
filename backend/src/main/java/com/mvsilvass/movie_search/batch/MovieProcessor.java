package com.mvsilvass.movie_search.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.dto.csv.MovieCsvRecord;
import com.mvsilvass.movie_search.model.*;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;

import java.time.format.DateTimeParseException;

import java.util.Collections;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MovieProcessor implements ItemProcessor<MovieCsvRecord, Movie> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Movie process(MovieCsvRecord csvRecord) {

        List<Genre> genres = parseJsonToList(csvRecord.getGenres(), new TypeReference<List<Genre>>() {});
        List<Keyword> keywords = parseJsonToList(csvRecord.getKeywords(), new TypeReference<List<Keyword>>() {});
        LocalDate releaseDate = parseLocalDate(csvRecord.getReleaseDate());

        return Movie.builder()
                .movieId(UUID.randomUUID())
                .title(csvRecord.getTitle())
                .overview(csvRecord.getOverview())
                .popularity(csvRecord.getPopularity())
                .voteAverage(csvRecord.getVoteAverage())
                .voteCount(csvRecord.getVoteCount())
                .runtime(csvRecord.getRuntime())
                .releaseDate(releaseDate)
                .keywords(keywords)
                .genres(genres)
        .build();
    }

    private <T> List<T> parseJsonToList(String value, TypeReference<List<T>> typeReference) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return objectMapper.readValue(value, typeReference);
    }

    private LocalDate parseLocalDate(String date) {
        if (date == null || date.isBlank())
            return null;

        try {
            return LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
