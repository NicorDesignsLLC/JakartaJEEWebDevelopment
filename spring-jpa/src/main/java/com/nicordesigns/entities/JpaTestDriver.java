package com.nicordesigns.entities;


import com.nicordesigns.config.SpringConfig;
import com.nicordesigns.entities.Actor;
import com.nicordesigns.entities.Movie;
import com.nicordesigns.entities.Studio;

import javax.persistence.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class JpaTestDriver {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MoviePersistenceUnit");

    public static void main(String[] args) {
    	
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        // Retrieve beans as needed
        try {
            createEntities();
            readEntities();
            updateEntity();
            deleteEntity();
        } finally {
            emf.close();
        }
    }

    // 1. CREATE (Insert new data)
    private static void createEntities() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Create Studio
        Studio studio = new Studio();
        studio.setStudioName("Warner Bros");
        studio.setYearFounded(1923);
        studio.setStudioHeadquarters("Burbank, California");

        // Create Actor
        Actor actor = new Actor();
        actor.setName("Leonardo DiCaprio");
        actor.setBirthDate(LocalDate.of(1974, 11, 11));
        actor.setNationality("American");

        // Create Movie
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setReleaseDate(LocalDate.of(2010, 7, 16));
        movie.setDuration(148);
        movie.setGenre("Sci-Fi");
        movie.setRating(new BigDecimal("8.8"));
        movie.setStudio(studio);

        // Establish relationships
        movie.getActors().add(actor);
        actor.getMovies().add(movie);

        // Persist Data
        em.persist(studio); // Studio saved first due to foreign key reference
        em.persist(actor);
        em.persist(movie);

        em.getTransaction().commit();
        em.close();

        System.out.println("\n✅ Data inserted successfully!");
    }

    // 2. READ (Find and Display Data)
    private static void readEntities() {
        EntityManager em = emf.createEntityManager();

        System.out.println("\n🔍 Reading Movies...");
        List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        for (Movie movie : movies) {
            System.out.println(movie);
        }

        System.out.println("\n🔍 Reading Actors...");
        List<Actor> actors = em.createQuery("SELECT a FROM Actor a", Actor.class).getResultList();
        for (Actor actor : actors) {
            System.out.println(actor);
        }

        em.close();
    }

    // 3. UPDATE (Modify an Existing Record)
    private static void updateEntity() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Find a movie by ID and update the title
        Movie movie = em.find(Movie.class, 1L);
        if (movie != null) {
            movie.setTitle("Inception - Director's Cut");
            em.merge(movie);
            System.out.println("\n✅ Movie updated: " + movie);
        }

        em.getTransaction().commit();
        em.close();
    }

    // 4. DELETE (Remove an Entity)
    private static void deleteEntity() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Find an actor and delete
        Actor actor = em.find(Actor.class, 1L);
        if (actor != null) {
            em.remove(actor);
            System.out.println("\n❌ Actor deleted: " + actor);
        }

        em.getTransaction().commit();
        em.close();
    }
}
