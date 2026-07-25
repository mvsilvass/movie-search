package com.mvsilvass.movie_search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.batch.MovieProcessor;
import com.mvsilvass.movie_search.batch.VectorStoreItemWriter;
import com.mvsilvass.movie_search.dto.csv.MovieCsvRecord;

import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.separator.DefaultRecordSeparatorPolicy;

import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;

import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.LineMapper;
import org.springframework.core.io.FileSystemResource;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;

import com.mvsilvass.movie_search.model.Movie;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final VectorStore vectorStore;

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

    @Bean
    public MovieProcessor itemProcessor(){
        return new MovieProcessor();
    }

    @Bean
    public ItemWriter<Movie> itemWriter(){
        return new VectorStoreItemWriter(vectorStore);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(25);
        return asyncTaskExecutor;
    }

    @Bean
    public Step importMoviesStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager) {
        return new StepBuilder("importMoviesStep", jobRepository)
                .<MovieCsvRecord, Movie>chunk(1000)
                .transactionManager(transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
        .build();
    }

    @Bean
    public Job importMoviesJob(JobRepository jobRepository,
                               Step importMoviesStep) {
        return new JobBuilder(jobRepository)
                .start(importMoviesStep)
        .build();
    }

}
