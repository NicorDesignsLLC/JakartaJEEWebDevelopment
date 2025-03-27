package com.nicordesigns.services;

import com.nicordesigns.entities.Movie;
import com.nicordesigns.dto.MovieSummaryDTO2;
import com.nicordesigns.repositories.MovieRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService2 {

    @Autowired
    private MovieRepository2 movieRepository2;

    @Transactional
    public Movie saveMovie(Movie movie) {
        return movieRepository2.save(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMoviesWithDetails() {
        return movieRepository2.findAllWithActorsAndStudio();
    }

    @Transactional(readOnly = true)
    public List<Movie> searchByTitle(String keyword) {
        return movieRepository2.findByTitleContainingIgnoreCase(keyword);
    }

    @Transactional(readOnly = true)
    public List<MovieSummaryDTO2> getMovieSummariesSorted(Sort sort) {
        return movieRepository2.fetchMovieSummaries(sort);
    }
}