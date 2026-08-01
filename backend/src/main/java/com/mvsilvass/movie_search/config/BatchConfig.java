package com.mvsilvass.movie_search.config;

import com.mvsilvass.movie_search.repository.MovieRepository;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;

import com.mvsilvass.movie_search.batch.MovieEmbeddingWriter;
import com.mvsilvass.movie_search.batch.MovieProcessor;
import com.mvsilvass.movie_search.dto.csv.MovieCsvRecord;

import org.springframework.ai.vectorstore.VectorStore;

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

    @Bean
    public MovieProcessor itemProcessor(){
        return new MovieProcessor();
    }

    @Bean
    public ItemWriter<Movie> itemWriter(MovieRepository movieRepository,
                                        VectorStore vectorStore){
        return new MovieEmbeddingWriter(movieRepository, vectorStore);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(25);
        return asyncTaskExecutor;
    }

    @Bean
    public Step importMoviesStep(JobRepository jobRepository,
                                 ItemReader<MovieCsvRecord> itemReader,
                                 ItemWriter<Movie> itemWriter,
                                 PlatformTransactionManager transactionManager) {
        return new StepBuilder("importMoviesStep", jobRepository)
                .<MovieCsvRecord, Movie>chunk(1000)
                .transactionManager(transactionManager)
                .reader(itemReader)
                .processor(itemProcessor())
                .writer(itemWriter)
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
