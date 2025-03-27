package com.nicordesigns;

import com.nicordesigns.config.JpaConfig;
import com.nicordesigns.dto.MovieSummaryDTO2;
import com.nicordesigns.services.MovieService2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;

import java.util.List;

public class MovieApp2 {

    public static void main(String[] args) {
        // Initialize Spring context using custom JPA config
        ApplicationContext context = new AnnotationConfigApplicationContext("com.nicordesigns.config");

        // Get the MovieService bean
        MovieService2 movieService2 = context.getBean(MovieService2.class);

        // Fetch movie summary projections, sorted by release date descending
        System.out.println("Fetching movies sorted by release date (newest first):");
        Sort sort = Sort.by(Sort.Direction.DESC, "releaseDate");
        List<MovieSummaryDTO2> summaries = movieService2.getMovieSummariesSorted(sort);

        // Display the projected results
        if (summaries.isEmpty()) {
            System.out.println("No movies found.");
        } else {
            for (MovieSummaryDTO2 summary : summaries) {
                System.out.println("--------------------------------------");
                System.out.println("Title: " + summary.getTitle());
                System.out.println("Studio: " + summary.getStudioName());
            }
        }

        ((AnnotationConfigApplicationContext) context).close();
    }
}