package com.mvsilvass.movie_search.batch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.springframework.batch.infrastructure.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.LineMapper;
import org.springframework.core.io.FileSystemResource;

import com.mvsilvass.movie_search.dto.csv.MovieCsvRecord;

@Configuration
public class MovieCsvReader {

    @Value("${app.movie-csv-path}")
    private String filePath;

    @Bean
    public FlatFileItemReader<MovieCsvRecord> itemReader(){
        return new FlatFileItemReaderBuilder<MovieCsvRecord>()
                .resource(new FileSystemResource(filePath))
                .recordSeparatorPolicy(
                        new DefaultRecordSeparatorPolicy()
                ).lineMapper(lineMapper())
                .name("csv-movie-reader")
                .linesToSkip(1)
                .build();
    }

    private LineMapper<MovieCsvRecord> lineMapper(){
        DefaultLineMapper<MovieCsvRecord> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = delimitedLineTokenizer();

        BeanWrapperFieldSetMapper<MovieCsvRecord> fieldSetMapper =
                new BeanWrapperFieldSetMapper<>();

        fieldSetMapper.setTargetType(MovieCsvRecord.class);
        fieldSetMapper.setDistanceLimit(0);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    private DelimitedLineTokenizer  delimitedLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setQuoteCharacter('"');
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(
                "budget", "genres", "homepage", "id", "keywords", "originalLanguage", "originalTitle", "overview",
                "popularity", "productionCompanies", "productionCountries", "releaseDate", "revenue",
                "runtime", "spokenLanguages", "status", "tagline", "title", "voteAverage", "voteCount"
        );

        return lineTokenizer;
    }

}
