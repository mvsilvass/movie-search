package com.mvsilvass.movie_search.csv.converter;

import com.mvsilvass.movie_search.csv.GenreCsv;
import com.opencsv.bean.AbstractBeanField;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Set;

public class JsonToGenreSetConverter extends AbstractBeanField<Set<GenreCsv>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Object convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.EMPTY_SET;
        }

        return objectMapper.readValue(value,new TypeReference<Set<GenreCsv>>(){});
    }
}
