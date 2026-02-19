package com.example.spring_batch_demo.repository;

import com.example.spring_batch_demo.model.Statistique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatistiqueRepository extends JpaRepository<Statistique, Long> {

}

