package com.example.spring_batch_demo.writer;

import com.example.spring_batch_demo.model.FullName;
import com.example.spring_batch_demo.model.Person;
import com.example.spring_batch_demo.service.PersonService;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component()
public class JdbcWriter5 implements ItemWriter<FullName> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWriter5(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public void write(Chunk<? extends FullName> chunk) throws Exception {
        List<? extends FullName> items = chunk.getItems();

        String sql = "INSERT INTO full_name (full_name, age, category) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, items, items.size(),
                (ps, item) -> {
                    ps.setString(1, item.getFullName());
                    ps.setInt(2, item.getAge());
                    ps.setString(3, item.getCategory());
                });

        System.out.println("Batch insert size = " + items.size());

    }

    /*
   @Bean
public JdbcBatchItemWriter<FullName> jdbcWriter(DataSource dataSource) {

    return new JdbcBatchItemWriterBuilder<FullName>()
            .dataSource(dataSource)
            .sql("INSERT INTO full_name (fullname, age) VALUES (:fullName, :age)")
            .beanMapped()
            .build();
}

}


    * */


}
