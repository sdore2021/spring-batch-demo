package com.example.spring_batch_demo.writer;

import com.example.spring_batch_demo.model.FullName;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component()
public class JdbcWriter6 {

    @Bean
    public JdbcBatchItemWriter<FullName> jdbcWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<FullName>()
                .dataSource(dataSource)
                .sql("INSERT INTO full_name (full_name, age) VALUES (:fullName, :age)")
                .beanMapped()
                .build();
    }
}

