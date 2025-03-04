package com.nicordesigns.repositories;

import com.nicordesigns.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Find movies by a partial match in the title
    List<Movie> findByMovieTitleContaining(String titleFragment);

    // Find movies based on Studio name (uses JPQL)
    @Query("SELECT m FROM Movie m WHERE m.studio.studioName = ?1")
    List<Movie> findByStudioName(String studioName);

    // Find movies by Studio ID
    List<Movie> findByStudioStudioId(Long studioId);

    // Find movies that have an Actor with the given name
    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE a.actorName = ?1")
    List<Movie> findByActorsActorName(String actorName);
}
