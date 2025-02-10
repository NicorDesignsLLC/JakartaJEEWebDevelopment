package com.nicordesigns.entities;

import com.nicordesigns.config.SpringConfig;
import com.nicordesigns.enums.Rating;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

public class JpaTestDriver {

    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        // Load Spring Context and Retrieve EntityManagerFactory Bean
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class)) {
            // Retrieve EntityManagerFactory from Spring
            emf = context.getBean(EntityManagerFactory.class);

            if (emf == null) {
                throw new IllegalStateException("❌ EntityManagerFactory bean not found! Check SpringConfig.");
            }

            createEntities();
            readEntities();
            updateEntity();
            deleteEntity();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    // 1. CREATE (Insert new data)
    private static void createEntities() {
        if (emf == null) {
            throw new IllegalStateException("❌ EntityManagerFactory is null! Cannot create entities.");
        }

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

        // Create Movies with Enum Ratings
        Movie movie1 = new Movie("Inception", LocalDate.of(2010, 7, 16), 148, "Sci-Fi", Rating.PG_13, studio);
        Movie movie2 = new Movie("Finding Nemo", LocalDate.of(2003, 5, 30), 100, "Animation", Rating.G, studio);

        // Establish relationships
        movie1.getActors().add(actor);
        movie2.getActors().add(actor);
        actor.getMovies().add(movie1);
        actor.getMovies().add(movie2);

        // Persist Data
        em.persist(studio);
        em.persist(actor);
        em.persist(movie1);
        em.persist(movie2);

        em.getTransaction().commit();
        em.close();

        System.out.println("\n✅ Movies inserted successfully with Ratings!");
    }

    // 2. READ (Find and Display Data)
    private static void readEntities() {
        EntityManager em = emf.createEntityManager();

        System.out.println("\n🔍 Reading Movies...");
        List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        for (Movie movie : movies) {
            System.out.println("🎬 " + movie.getTitle() + " | Genre: " + movie.getGenre() + " | Rating: " + movie.getRating());
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

        Long movieId = 1L;  // Specify which movie
        Long actorId = 1L;  // Specify which actor to remove

        // Find the movie
        Movie movie = em.find(Movie.class, movieId);
        Actor actor = em.find(Actor.class, actorId);

        if (movie != null && actor != null) {
            if (movie.getActors().contains(actor)) {
                movie.getActors().remove(actor);
                em.merge(movie);  // Update the movie in the database
                System.out.println("\n✅ Actor " + actor.getName() + " removed from Movie " + movie.getTitle());
            } else {
                System.out.println("\n⚠️ Actor not found in the specified movie.");
            }
        } else {
            System.out.println("\n❌ Movie or Actor not found.");
        }

        em.getTransaction().commit();
        em.close();
    }

}
