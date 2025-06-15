package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class Database {
    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("yearup");  // <-- change if needed
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
