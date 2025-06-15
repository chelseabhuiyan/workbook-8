package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager manager = new DataManager();

        // Actor search by last name
        List<Actor> matches;
        do {
            System.out.print("Enter an actor's last name: ");
            String lastName = scanner.nextLine();
            matches = manager.findActorsByLastName(lastName);

            if (matches.isEmpty()) {
                System.out.println("No actors found. Try again.");
            }
        } while (matches.isEmpty());

        // Show matching actors
        System.out.println("\nMatching actors:");
        for (Actor actor : matches) {
            System.out.println(actor);
        }

        // Get actor ID for movie list
        System.out.print("\nEnter an actor ID to view their films: ");
        int actorId = Integer.parseInt(scanner.nextLine());

        List<Film> films = manager.getFilmsByActorId(actorId);

        if (films.isEmpty()) {
            System.out.println("No films found for this actor.");
        } else {
            System.out.println("\nMovies:");
            for (Film film : films) {
                System.out.println("---------------");
                System.out.println(film);
            }
        }

        scanner.close();
    }
}
