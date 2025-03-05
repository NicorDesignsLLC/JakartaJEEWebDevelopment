package com.nicordesigns.repositories;

import com.nicordesigns.entities.Movie;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	
	 // ✅ Find all movies and fetch actors eagerly
    @EntityGraph(attributePaths = {"actors","studio"})
    List<Movie> findAll();
    
    // Find movies by a partial match in the title
	List<Movie> findByTitleContaining(String titleFragment);
	
    // Find movies based on Studio name (uses JPQL)
    @Query("SELECT m FROM Movie m WHERE m.studio.studioName = ?1")
    List<Movie> findByStudioName(String studioName);
   
    
}
