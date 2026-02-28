package com.example.spring_batch_demo.writer;

import com.example.spring_batch_demo.model.Person;
import com.example.spring_batch_demo.service.PersonService;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component()
public class Writter1 implements ItemWriter<Person> {

    @Autowired
    private final PersonService personService;

    public Writter1(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {

        List<Person> items = (List<Person>) chunk.getItems();
        //System.out.println("writing..." + items.size());
        // Sauvegarde en DB
        personService.saveAll(items);

    }


}

