package com.nicordesigns;

import com.nicordesigns.config.SpringConfig;
import com.nicordesigns.dto.MovieDTO;
import com.nicordesigns.services.MovieService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class MovieApp {
    public static void main(String[] args) {
        // Initialize Spring application context using SpringConfig
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        // Retrieve the MovieService bean
        MovieService movieService = context.getBean(MovieService.class);

        // Fetch all movies from the database
        List<MovieDTO> movies = movieService.getAllMovies();

        // Display the movies
        if (movies.isEmpty()) {
            System.out.println("No movies found in the database.");
        } else {
            System.out.println("Movies in the database:");
            for (MovieDTO movie : movies) {
                System.out.println("--------------------------------------");
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Release Date: " + movie.getReleaseDate());
                System.out.println("Duration: " + movie.getDuration() + " minutes");
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Rating: " + movie.getRating());
                System.out.println("Studio: " + movie.getStudioName());
                System.out.println("Actors: " + String.join(", ", movie.getActorNames()));
            }
        }

        // Close the application context
        ((AnnotationConfigApplicationContext) context).close();
    }
}
