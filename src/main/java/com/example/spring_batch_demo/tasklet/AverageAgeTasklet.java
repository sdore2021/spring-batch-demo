package com.example.spring_batch_demo.tasklet;

import com.example.spring_batch_demo.model.Statistique;
import com.example.spring_batch_demo.service.PersonService;
import com.example.spring_batch_demo.service.StatistiqueService;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AverageAgeTasklet implements Tasklet {

    @Autowired
    private PersonService personService;

    @Autowired
    private StatistiqueService stats;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Double avgAge = personService.calculateAverageAge();

        System.out.println("Age moyen = " + avgAge);

        // chercher si statistique existe déjà (id = 1)
        Optional<Statistique> optional = stats.findById(1L);

        Statistique element;

        if(optional.isPresent()){
            element = optional.get();   // update
        } else {
            element = new Statistique(); // insert première fois
        }


        element.setMoyenne(avgAge);

        stats.save(element);

        return RepeatStatus.FINISHED;
    }
}

