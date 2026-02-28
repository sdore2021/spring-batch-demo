package com.example.spring_batch_demo.reader;

import com.example.spring_batch_demo.domain.ReaderJDBC;
import com.example.spring_batch_demo.model.NombreByDateAge;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class JdbcReaderByCreatedAt {

    @Bean("jdbCreated")
    @StepScope
    public JdbcCursorItemReader<NombreByDateAge> personReader(
            @Value("#{jobParameters['dateDebut']}") String dateDebut,
            @Value("#{jobParameters['dateFin']}") String dateFin
    ){

        // Cas 3 : si dateDebut est null ou vide, prendre la date du jour
        if (dateDebut == null || dateDebut.isBlank()) {
            dateDebut = java.time.LocalDate.now().toString();
        }

        // Cas 2 : si dateFin vide ou null, utiliser dateDebut
        if (dateFin == null || dateFin.isBlank()) {
            dateFin = dateDebut;
        }

        String sql =
                """
                        SELECT date_jour AS created_at, age, COUNT(*) AS nombre
                        FROM (
                            SELECT DATE(created_at) AS date_jour, age
                            FROM public.person
                        WHERE DATE(created_at) >= ? AND DATE(created_at)<= ?
                        ) t
                        GROUP BY GROUPING SETS (
                            (date_jour),
                            (date_jour, age)
                        )
                        ORDER BY date_jour, age;
                """;
        return ReaderJDBC.createReader(
                sql,
                dateDebut,
                dateFin,
                ((rs, rowNum) -> {
                    NombreByDateAge n = new NombreByDateAge();

                    n.setCreatedAt(rs.getDate("created_at"));
                    n.setNombre(rs.getInt("nombre"));
                    Integer age = rs.getObject("age", Integer.class);
                    n.setAge(age);

                    return n;
                }),
                500);
    }
}
