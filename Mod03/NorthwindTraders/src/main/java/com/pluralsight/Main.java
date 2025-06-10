package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root"; // update if needed
        String password = "yearup"; // update with your actual MySQL password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            String sql = """
                  SELECT ProductName
                  FROM Products
                  """;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                System.out.println(results.getString("ProductName"));
            }

            results.close();
            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
