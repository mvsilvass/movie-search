package com.mvsilvass.movie_search.csv.converter;

import com.mvsilvass.movie_search.csv.model.GenreRecord;
import com.opencsv.bean.AbstractBeanField;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

public class GenreRecordConverter extends AbstractBeanField<List<GenreRecord>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Object convert(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return objectMapper.readValue(value, new TypeReference<List<GenreRecord>>(){});
    }
}
