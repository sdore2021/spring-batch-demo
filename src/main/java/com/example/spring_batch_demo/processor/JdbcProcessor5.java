package com.example.spring_batch_demo.processor;

import com.example.spring_batch_demo.model.FullName;
import com.example.spring_batch_demo.model.Person;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class JdbcProcessor5 implements ItemProcessor<FullName,FullName> {


    @Override
    public @Nullable FullName process(@NotNull FullName item) throws Exception {
        if(item.getAge() < 35) {
            item.setCategory("Jeune");
        } else if (item.getAge() <40) {
            item.setCategory("Adulte");
        } else {
            item.setCategory("Vieux");
        }
        System.out.println("Processing 1....." + item.toString());
        return item;
    }
}
