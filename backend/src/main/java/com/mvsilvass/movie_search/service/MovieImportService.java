package com.mvsilvass.movie_search.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.job.Job;

@Service
@RequiredArgsConstructor
public class MovieImportService {

    private final JobOperator jobOperator;
    private final Job movieImportJob;

    public void execute() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        jobOperator.start(movieImportJob, jobParameters);
    }
}