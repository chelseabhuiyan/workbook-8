package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";        // update as needed
        String password = "";  // update as needed

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("  1) Display all products");
            System.out.println("  2) Display all customers");
            System.out.println("  0) Exit");
            System.out.print("Select an option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            switch (option) {
                case 1 -> displayProducts(url, user, password);
                case 2 -> displayCustomers(url, user, password);
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void displayProducts(String url, String user, String password) {
        String sql = """
            SELECT ProductID,
                   ProductName,
                   UnitPrice,
                   UnitsInStock
            FROM Products
            ORDER BY ProductName
            """;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(sql);
            results = statement.executeQuery();

            System.out.printf("%-5s %-35s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
            System.out.println("---------------------------------------------------------------");

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.printf("%-5d %-35s $%-9.2f %-10d%n", id, name, price, stock);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (results != null) results.close(); } catch (SQLException ignored) {}
            try { if (statement != null) statement.close(); } catch (SQLException ignored) {}
            try { if (connection != null) connection.close(); } catch (SQLException ignored) {}
        }
    }

    private static void displayCustomers(String url, String user, String password) {
        String sql = """
            SELECT ContactName,
                   CompanyName,
                   City,
                   Country,
                   Phone
            FROM Customers
            ORDER BY Country
            """;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(sql);
            results = statement.executeQuery();

            System.out.printf("%-30s %-30s %-15s %-15s %-15s%n", "Contact", "Company", "City", "Country", "Phone");
            System.out.println("-----------------------------------------------------------------------------------------------");

            while (results.next()) {
                String contact = results.getString("ContactName");
                String company = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.printf("%-30s %-30s %-15s %-15s %-15s%n", contact, company, city, country, phone);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (results != null) results.close(); } catch (SQLException ignored) {}
            try { if (statement != null) statement.close(); } catch (SQLException ignored) {}
            try { if (connection != null) connection.close(); } catch (SQLException ignored) {}
        }
    }
}
