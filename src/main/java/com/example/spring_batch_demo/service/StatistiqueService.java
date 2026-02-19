package com.example.spring_batch_demo.service;
import com.example.spring_batch_demo.model.Statistique;
import com.example.spring_batch_demo.repository.StatistiqueRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatistiqueService {

    private final StatistiqueRepository stats;

    public StatistiqueService(StatistiqueRepository statistiqueRepository) {
        this.stats = statistiqueRepository;
    }


    public Statistique save(Statistique statistique) {
        return stats.save(statistique);
    }

    public Statistique saveAndFlush(Statistique statistique) {
        return stats.saveAndFlush(statistique);
    }

    public Optional<Statistique> findById(Long id){
        return stats.findById(id);
    }


}



