package com.example.spring_batch_demo.reader;

import com.example.spring_batch_demo.domain.ReaderJDBC;
import com.example.spring_batch_demo.model.FullName;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JdbcReader5 {

    @Bean("jdbcreader")
    @StepScope
    public JdbcCursorItemReader<FullName> personReader(){
        String sql = "SELECT * From Person";
        return ReaderJDBC.createReader(
                sql,
                ((rs, rowNum) -> {
                    FullName fullName = new FullName();
                    fullName.setFullName(String.format("%s-%s",rs.getString("prenom"),rs.getString("nom")));
                    fullName.setAge(rs.getInt("age"));
                    return fullName;
                }),
                5);
    }
}
