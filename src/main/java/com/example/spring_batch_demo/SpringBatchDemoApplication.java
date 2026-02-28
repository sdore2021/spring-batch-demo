package com.example.spring_batch_demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchDemoApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDemoApplication.class, args);
    }

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job myJob;

    @Override
    public void run(String... args) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("dateDebut", "2026-01-01")
                .addString("dateFin", "2026-01-03")
                .addDate("runDate", new java.util.Date())
                .toJobParameters();

        jobLauncher.run(myJob, params);
    }
}