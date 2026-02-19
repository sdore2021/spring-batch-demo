package com.example.spring_batch_demo.tasklet;

import com.example.spring_batch_demo.service.PersonService;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CleanTasklet implements Tasklet {

    @Autowired
    private PersonService personService;

    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("clean Person table");
        personService.cleanTable();
        return RepeatStatus.FINISHED;
    }
}

