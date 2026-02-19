package com.example.spring_batch_demo.reader;

import com.example.spring_batch_demo.model.Person;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Reader1 {

    @Bean("readerCSV")
    @StepScope
    public FlatFileItemReader<Person> reader() throws IOException {
        Resource resource;
        resource = new FileSystemResource("src/main/resources/file1.csv");
        System.out.println("reading... " + resource.getFile());

        final var fieldSetMapper = new BeanWrapperFieldSetMapper<Person>();
        fieldSetMapper.setTargetType(Person.class);
        fieldSetMapper.setDistanceLimit(0);


        return new FlatFileItemReaderBuilder<Person>()
                .name("readerCSV")
                .resource(resource)
                .delimited()
                .delimiter(";")
                .names("nom", "prenom","age")
                .fieldSetMapper(fieldSetMapper)
                .linesToSkip(1) // skip header
                .build();

    }
}
