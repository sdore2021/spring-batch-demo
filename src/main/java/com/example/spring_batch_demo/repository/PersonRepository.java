package com.example.spring_batch_demo.repository;

import com.example.spring_batch_demo.model.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT AVG(p.age) FROM Person p")
    Double calculateAverageAge();

    @Query("SELECT MIN(p.age) FROM Person p")
    int minAge();

    @Query("SELECT MAX(p.age) FROM Person p")
    int maxAge();

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE person", nativeQuery = true)
    void cleanTable();

}

