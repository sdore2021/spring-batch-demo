package com.example.spring_batch_demo.processor;

import com.example.spring_batch_demo.model.Person;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor1 implements ItemProcessor<Person,Person> {


    @Override
    public @Nullable Person process(@NotNull Person item) throws Exception {
        System.out.println("Processing 1....." + item.getInformation());

        if(item.getAge() <= 28) return null;

        return item;
    }
}
