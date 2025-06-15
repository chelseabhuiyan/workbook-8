package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public List<Actor> findActorsByLastName(String lastName) {
        List<Actor> actors = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";

        try (
                Connection conn = Database.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, lastName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actors.add(new Actor(
                            rs.getInt("actor_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actors;
    }

    public List<Film> getFilmsByActorId(int actorId) {
        List<Film> films = new ArrayList<>();
        String sql = """
            SELECT f.film_id, f.title, f.description, f.release_year, f.length
            FROM film f
            JOIN film_actor fa ON f.film_id = fa.film_id
            WHERE fa.actor_id = ?
            ORDER BY f.title
        """;

        try (
                Connection conn = Database.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, actorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    films.add(new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("release_year"),
                            rs.getInt("length")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return films;
    }
}
