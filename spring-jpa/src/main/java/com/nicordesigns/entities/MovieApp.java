package com.nicordesigns.entities;

import com.nicordesigns.config.SpringConfig;
import com.nicordesigns.dto.MovieDTO;
import com.nicordesigns.services.MovieService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MovieApp {
    public static void main(String[] args) {
        // Initialize Spring application context using SpringConfig
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        // Retrieve the MovieService bean
        MovieService movieService = context.getBean(MovieService.class);

        // Create and save a new movie
        MovieDTO newMovie = new MovieDTO();
        newMovie.setMovieTitle("Inception");
        newMovie.setMovieReleaseDate(new Date());
        newMovie.setMovieDuration(148);
        newMovie.setMovieGenre("Sci-Fi");
        newMovie.setMovieRating("PG-13");
        newMovie.setStudioName("Warner Bros.");
        newMovie.setActorNames(Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page"));

        // Save the movie using MovieService
        MovieDTO savedMovie = movieService.createMovie(newMovie);
        System.out.println("Movie saved: " + savedMovie.getMovieTitle() + " by " + savedMovie.getStudioName());

        // Retrieve the movie by ID and print details
        MovieDTO fetchedMovie = movieService.getMovieById(savedMovie.getMovieId());
        System.out.println("Fetched Movie: " + fetchedMovie.getMovieTitle());
        System.out.println("Studio: " + fetchedMovie.getStudioName());
        System.out.println("Actors: " + String.join(", ", fetchedMovie.getActorNames()));

        // Update the movie title
        movieService.updateMovieTitle(savedMovie.getMovieId(), "Inception - Director's Cut");
        System.out.println("Updated movie title: " + movieService.getMovieById(savedMovie.getMovieId()).getMovieTitle());

        // Close the application context
        ((AnnotationConfigApplicationContext) context).close();
    }
}
