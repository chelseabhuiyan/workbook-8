package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = ""; //put in password

        String sql = """
                SELECT ProductID,
                       ProductName,
                       UnitPrice,
                       UnitsInStock
                FROM Products
                ORDER BY ProductName
                """;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            System.out.printf("%-5s %-35s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
            System.out.println("---------------------------------------------------------------");

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-35s $%-9.2f %-10d%n", id, name, price, stock);
            }

            results.close();
            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
