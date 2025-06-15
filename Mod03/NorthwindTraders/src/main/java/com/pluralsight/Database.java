package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class Database {
    private static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("yearup");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
