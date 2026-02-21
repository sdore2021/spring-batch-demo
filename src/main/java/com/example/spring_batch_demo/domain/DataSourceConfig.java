package com.example.spring_batch_demo.domain;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {
    public static DataSource dataSource(){

        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/mydbTest";
        String username = "sam";
        String password = "sam";

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        return ds;
    }
}
