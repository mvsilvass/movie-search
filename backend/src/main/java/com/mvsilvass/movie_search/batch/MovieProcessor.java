package com.mvsilvass.movie_search.batch;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.dto.csv.MovieCsvRecord;
import com.mvsilvass.movie_search.model.*;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MovieProcessor implements ItemProcessor<MovieCsvRecord, Movie> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Movie process(MovieCsvRecord csvRecord) throws Exception {

        List<Genre> genres = parseJsonToList(csvRecord.getGenres(), new TypeReference<List<Genre>>() {});
        List<Keyword> keywords = parseJsonToList(csvRecord.getKeywords(), new TypeReference<List<Keyword>>() {});


        return new Movie(
                UUID.randomUUID(),
                csvRecord.getTitle(),
                csvRecord.getOverview(),
                csvRecord.getPopularity(),
                parseLocalDate(csvRecord.getReleaseDate()),
                csvRecord.getRuntime(),
                csvRecord.getVoteAverage(),
                csvRecord.getVoteCount(),
                genres,
                keywords
        );
    }

    private <T> List<T> parseJsonToList(String value, TypeReference<List<T>> typeReference) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return objectMapper.readValue(value, typeReference);
    }

    public static LocalDate parseLocalDate(String date) {
        if (date == null || date.isBlank())
            return null;

        try {
            return LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
