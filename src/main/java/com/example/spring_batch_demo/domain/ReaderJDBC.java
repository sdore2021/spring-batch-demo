package com.example.spring_batch_demo.domain;

import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.core.RowMapper;

public class ReaderJDBC extends DataSourceConfig {

    public static <T> JdbcCursorItemReader<T> createReader(
            String sql,
            RowMapper<T> rowMapper,
            int fetchSize
    ){
        return new JdbcCursorItemReaderBuilder<T>()
                .name("jbdcReader")
                .dataSource(dataSource())
                .sql(sql)
                .rowMapper(rowMapper)
                .saveState(false)
                .connectionAutoCommit(false)
                .fetchSize(fetchSize)
                .build();
    }

}
