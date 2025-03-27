package com.nicordesigns.repositories;
import com.nicordesigns.entities.Movie;
import com.nicordesigns.dto.MovieSummaryDTO2;
import org.springframework.data.domain.Sort;  // Add this import
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository2 extends PagingAndSortingRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String titleFragment);

    @Query("SELECT m FROM Movie m WHERE m.studio.studioName = ?1")
    List<Movie> findByStudioName(String studioName);

    @EntityGraph(attributePaths = {"actors", "studio"})
    @Query("SELECT m FROM Movie m")
    List<Movie> findAllWithActorsAndStudio();

    @Query("SELECT new com.nicordesigns.dto.MovieSummaryDTO2(m.title, m.studio.studioName) FROM Movie m")
    List<MovieSummaryDTO2> fetchMovieSummaries(Sort sort);
}