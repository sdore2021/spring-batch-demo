package com.example.spring_batch_demo.domain;

import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.time.LocalDate;

public class ReaderJDBC extends DataSourceConfig {

    public static <T> JdbcCursorItemReader<T> createReader(
            String sql,
            String dateDebut,
            String dateFin,
            RowMapper<T> rowMapper,
            int fetchSize
    ){

        return new JdbcCursorItemReaderBuilder<T>()
                .name("jbdcReader")
                .dataSource(dataSource())
                .sql(sql)
                .preparedStatementSetter(ps -> {
                    ps.setDate(1, Date.valueOf(dateDebut));
                    ps.setDate(2, Date.valueOf(dateFin));
                })
                .rowMapper(rowMapper)
                .saveState(false)
                .connectionAutoCommit(false)
                .fetchSize(fetchSize)
                .build();
    }

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
