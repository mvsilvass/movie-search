package com.mvsilvass.movie_search.service;

import com.mvsilvass.movie_search.repository.MovieRepository;
import org.springframework.batch.core.job.JobInstance;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.Job;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieImportService {

    private final MovieRepository movieRepository;
    private final JobOperator jobOperator;
    private final Job movieImportJob;

    public JobExecution execute() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        return jobOperator.start(movieImportJob, jobParameters);
    }

    public boolean isDatabasePopulated() {
        return movieRepository.existsBy();
    }
}