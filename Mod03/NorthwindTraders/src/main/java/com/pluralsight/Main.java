package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String user = "root";
        String password = "";

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("  1) Display all products");
            System.out.println("  2) Display all customers");
            System.out.println("  3) Display all categories and filter products");
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
                case 3 -> displayCategoriesAndProducts(scanner, url, user, password);
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void displayProducts(String url, String user, String password) {
        String sql = """
            SELECT ProductID, ProductName, UnitPrice, UnitsInStock
            FROM Products
            ORDER BY ProductName
        """;

        try (
                Connection connection = Database.getDataSource().getConnection();
// Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            System.out.printf("%-5s %-35s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
            System.out.println("---------------------------------------------------------------------");

            while (results.next()) {
                System.out.printf("%-5d %-35s $%-9.2f %-10d%n",
                        results.getInt("ProductID"),
                        results.getString("ProductName"),
                        results.getDouble("UnitPrice"),
                        results.getInt("UnitsInStock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCustomers(String url, String user, String password) {
        String sql = """
            SELECT ContactName, CompanyName, City, Country, Phone
            FROM Customers
            ORDER BY Country
        """;

        try (
                Connection connection = Database.getDataSource().getConnection();

                // Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            System.out.printf("%-30s %-30s %-15s %-15s %-15s%n",
                    "Contact", "Company", "City", "Country", "Phone");
            System.out.println("-----------------------------------------------------------------------------------------------");

            while (results.next()) {
                System.out.printf("%-30s %-30s %-15s %-15s %-15s%n",
                        results.getString("ContactName"),
                        results.getString("CompanyName"),
                        results.getString("City"),
                        results.getString("Country"),
                        results.getString("Phone")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCategoriesAndProducts(Scanner scanner, String url, String user, String password) {
        String categorySql = """
            SELECT CategoryID, CategoryName
            FROM Categories
            ORDER BY CategoryID
        """;

        try (
                Connection connection = Database.getDataSource().getConnection();
                //Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement categoryStmt = connection.prepareStatement(categorySql);
                ResultSet categories = categoryStmt.executeQuery()
        ) {
            System.out.printf("%-10s %-30s%n", "CategoryID", "Category Name");
            System.out.println("---------------------------------------------");

            while (categories.next()) {
                System.out.printf("%-10d %-30s%n",
                        categories.getInt("CategoryID"),
                        categories.getString("CategoryName"));
            }

            System.out.print("\nEnter a Category ID to view its products: ");
            int categoryId = Integer.parseInt(scanner.nextLine());

            String productSql = """
                SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                FROM Products
                WHERE CategoryID = ?
                ORDER BY ProductName
            """;

            try (
                    PreparedStatement productStmt = connection.prepareStatement(productSql)
            ) {
                productStmt.setInt(1, categoryId);
                try (ResultSet products = productStmt.executeQuery()) {
                    System.out.printf("\n%-5s %-35s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
                    System.out.println("---------------------------------------------------------------");

                    while (products.next()) {
                        System.out.printf("%-5d %-35s $%-9.2f %-10d%n",
                                products.getInt("ProductID"),
                                products.getString("ProductName"),
                                products.getDouble("UnitPrice"),
                                products.getInt("UnitsInStock")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Category ID. Please enter a number.");
        }
    }
}
