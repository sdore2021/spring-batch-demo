package com.example.spring_batch_demo.service;


import com.example.spring_batch_demo.model.Person;
import com.example.spring_batch_demo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> saveAll(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Double calculateAverageAge(){
        return personRepository.calculateAverageAge();
    }

    public int minAge(){
        return personRepository.minAge();
    }

    public int maxAge(){
        return personRepository.maxAge();
    }

    public void cleanTable(){
        personRepository.cleanTable();
    }
}


