package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for last name
        System.out.print("Enter the last name of an actor: ");
        String lastName = scanner.nextLine();

        // Show actors with that last name
        try (
                Connection connection = Database.getDataSource().getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?")
        ) {
            stmt.setString(1, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Matches found:");
                    do {
                        System.out.printf("ID: %d | %s %s%n",
                                rs.getInt("actor_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"));
                    } while (rs.next());
                } else {
                    System.out.println("No actors found with last name: " + lastName);
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching actors: " + e.getMessage());
            return;
        }

        // Prompt for full name
        System.out.print("\nEnter full actor name (First Last): ");
        String[] fullName = scanner.nextLine().split(" ");
        if (fullName.length < 2) {
            System.out.println("Invalid name. Format must be: First Last");
            return;
        }

        String firstName = fullName[0];
        lastName = fullName[1];

        // Query films
        try (
                Connection connection = Database.getDataSource().getConnection();
                PreparedStatement stmt = connection.prepareStatement("""
                SELECT f.title
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                JOIN actor a ON fa.actor_id = a.actor_id
                WHERE a.first_name = ? AND a.last_name = ?
                ORDER BY f.title
            """)
        ) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nMovies for " + firstName + " " + lastName + ":");
                    do {
                        System.out.println("- " + rs.getString("title"));
                    } while (rs.next());
                } else {
                    System.out.println("No movies found for " + firstName + " " + lastName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching movies: " + e.getMessage());
        }

        scanner.close();
    }
}
