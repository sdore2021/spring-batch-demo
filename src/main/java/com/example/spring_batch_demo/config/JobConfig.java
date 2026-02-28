package com.example.spring_batch_demo.config;

import com.example.spring_batch_demo.model.*;
import com.example.spring_batch_demo.processor.JdbcProcessor5;
import com.example.spring_batch_demo.processor.Processor1;
import com.example.spring_batch_demo.tasklet.AverageAgeTasklet;
import com.example.spring_batch_demo.tasklet.CleanTasklet;
import com.example.spring_batch_demo.tasklet.MaxAgeTasklet;
import com.example.spring_batch_demo.tasklet.MinAgeTasklet;
import com.example.spring_batch_demo.writer.JdbcWriter5;
import com.example.spring_batch_demo.writer.Writter1;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class JobConfig {

    @Bean
    public Job myJob(
            JobRepository jobRepository,
            Flow flow1,
            Flow flow2,
            Step jdbcStep5
    ){
        return new JobBuilder("myJob", jobRepository)
                .start(flow1)
                .next(flow2)
                .next(jdbcStep5)
                .end()
                .build();
    }

    @Bean
    public Flow flow1(Step cleanUp, Step step1){
        return new FlowBuilder<Flow>("cleanUPFlow")
                .start(cleanUp)
                .next(step1)
                .build();
    }

    @Bean
    public Flow flow2(Step step2, Step step3, Step step4){
        return new FlowBuilder<Flow>("calculStats")
                .start(step2)
                .next(step3)
                .next(step4)
                .build();

    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }


    @Bean
    public Step step1(
                      JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      @Qualifier("readerCSV") FlatFileItemReader<Person> reader1,
                      Processor1 processor1,
                      Writter1 writter1
    ) throws IOException {
        return new StepBuilder("step1",jobRepository)
                .<Person, Person>chunk(5).transactionManager(transactionManager)
                .reader(reader1)
                .processor(processor1)
                .writer(writter1)
                .build();

    }

    @Bean
    public Step step2(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      AverageAgeTasklet tasklet) {

        return new StepBuilder("step2", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Step step3(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      MinAgeTasklet tasklet) {

        return new StepBuilder("step3", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Step step4(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      MaxAgeTasklet tasklet) {

        return new StepBuilder("step4", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean
    public Step cleanUp(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      CleanTasklet tasklet) {

        return new StepBuilder("cleanUp", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }


    @Bean
    public Step jdbcStep5(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("jdbCreated") JdbcCursorItemReader<NombreByDateAge> jdbcCreatedr5,
            ClassifierCompositeItemWriter<NombreByDateAge> writer
    ) throws IOException {
        return new StepBuilder("jdbcStep5",jobRepository)
                .<NombreByDateAge, NombreByDateAge>chunk(500).transactionManager(transactionManager)
                .reader(jdbcCreatedr5)
                .writer(writer)    // redirection vers le bon writer
                .build();

    }

    @Bean
    public ClassifierCompositeItemWriter<NombreByDateAge> classifierWriter(
            JdbcBatchItemWriter<NombreByDateAge> writerTotal,
            JdbcBatchItemWriter<NombreByDateAge> writerByAge) {

        ClassifierCompositeItemWriter<NombreByDateAge> writer =
                new ClassifierCompositeItemWriter<>();

        writer.setClassifier(item -> {
            if (item.getAge() == null) {
                return writerTotal;
            } else {
                return writerByAge;
            }
        });

        return writer;
    }


    @Bean
    public JdbcBatchItemWriter<NombreByDateAge> writerTotal(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<NombreByDateAge>()
                .dataSource(dataSource)
                .sql("""
                INSERT INTO Nombre_by_date
                (created_at, nombre)
                VALUES (:createdAt, :nombre)
            """)
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<NombreByDateAge> writerByAge(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<NombreByDateAge>()
                .dataSource(dataSource)
                .sql("""
                INSERT INTO Nombre_by_date_age
                (created_at,age, nombre)
                VALUES (:createdAt, :age, :nombre)
            """)
                .beanMapped()
                .build();
    }


}
