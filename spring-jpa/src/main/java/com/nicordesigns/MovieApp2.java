package com.nicordesigns;

import com.nicordesigns.services.MovieService2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;

public class MovieApp2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.nicordesigns.config");
        MovieService2 movieService = context.getBean(MovieService2.class);
        System.out.println("Fetching movies sorted by release date (newest first):");
        movieService.getMovieSummariesSorted(Sort.by(Sort.Direction.DESC, "releaseDate"))
                    .forEach(summary -> {
                        System.out.println("--------------------------------------");
                        System.out.println("Title: " + summary.getTitle());
                        System.out.println("Studio: " + summary.getStudioName());
                        System.out.println("Release Date: " + summary.getReleaseDate());
                    });
        context.close();
    }
}