
Tutorial Outline: Eliminating Boilerplate JDBC Code with Spring Data JPA

Introduction
Objective: Teach how Spring Data JPA eliminates repetitive JDBC boilerplate code and simplifies data access in a Jakarta EE 8 application.

Target Audience: Java developers familiar with basic JDBC and Jakarta EE, looking to streamline persistence with Spring Data JPA.

Prerequisites: Basic knowledge of Java, Maven, JDBC, and relational databases (e.g., MySQL or H2).
Tools: OpenJDK 11, Maven, Jakarta EE 8, Spring Boot (for simplicity), and an IDE (e.g., IntelliJ IDEA or Eclipse).

Example Context: A simple domain model with Movie, Actor, and Studio entities to demonstrate concepts.

1. Setting Up the Project: Maven Dependencies

Goal: Configure a working Jakarta EE 8 project with Spring Data JPA and OpenJDK 11 compatibility.

Steps:

Create a new Maven project with pom.xml.
Add dependencies for:

Jakarta EE 8: jakarta.platform:jakarta.jakartaee-api:8.0.0 (scope: provided).
Spring Boot: org.springframework.boot:spring-boot-starter-web (to simplify setup).
Spring Data JPA: org.springframework.boot:spring-boot-starter-data-jpa.
Database Driver: e.g., com.h2database:h2 (in-memory database for simplicity).
OpenJDK 11 Compatibility: Ensure Maven uses Java 11 (maven-compiler-plugin with source and target set to 11).
Example pom.xml snippet:
xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.7.18</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        <version>2.7.18</version>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.2.224</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.platform</groupId>
        <artifactId>jakarta.jakartaee-api</artifactId>
        <version>8.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>11</source>
                <target>11</target>
            </configuration>
        </plugin>
    </plugins>
</build>

Verification: Run mvn clean install and ensure the project builds successfully.
2. Understanding Spring Data’s Unified Data Access
Goal: Explain how Spring Data JPA avoids code duplication compared to raw JDBC.
Key Points:
JDBC Boilerplate: Show a traditional JDBC example (e.g., connecting, querying, mapping results manually for a Movie entity).
Spring Data JPA Advantage: Introduce how it provides a unified abstraction over JPA, reducing manual SQL and connection management.
Core Concepts:
Repositories as central interfaces for data access.
Automatic implementation of CRUD operations.
Query derivation from method names.
Example Transition:
JDBC: 20+ lines to fetch movies by title.
Spring Data JPA: 1-line repository method (List<Movie> findByTitle(String title);).
3. Defining the Domain Model
Goal: Set up Movie, Actor, and Studio entities to use throughout the tutorial.
Code Example:
java
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    private Studio studio;
    @ManyToMany
    private List<Actor> actors = new ArrayList<>();
    // Getters, setters, constructors
}

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();
    // Getters, setters, constructors
}

@Entity
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "studio")
    private List<Movie> movies = new ArrayList<>();
    // Getters, setters, constructors
}
Explanation: Use JPA annotations to define relationships (@ManyToOne, @ManyToMany, @OneToMany).
4. Using Stock Repository Interfaces
Goal: Demonstrate basic CRUD operations with Spring Data’s built-in repositories.
Steps:
Define a repository: public interface MovieRepository extends JpaRepository<Movie, Long> {}
Use methods like save(), findById(), findAll(), delete() without implementation.
Example:
java
@Autowired
private MovieRepository movieRepository;

public void demoStockRepo() {
    Movie movie = new Movie("Inception");
    movieRepository.save(movie); // Persist
    List<Movie> movies = movieRepository.findAll(); // Retrieve all
    System.out.println(movies);
}
Benefit: No boilerplate JDBC code for connections, statements, or result mapping.
5. Creating Query Methods for Finding Entities
Goal: Show how to define custom queries using method names.
Examples:
List<Movie> findByTitle(String title);
List<Movie> findByStudioName(String studioName);
List<Movie> findByActorsNameContaining(String actorNamePart);
Code:
java
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
    List<Movie> findByStudioName(String studioName);
}
Explanation: Spring Data parses method names to generate SQL queries automatically.
6. Providing Custom Method Implementations
Goal: Add a custom query requiring specific logic.
Steps:
Extend the repository interface with a custom method.
Provide an implementation using @Query.
Example:
java
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE SIZE(m.actors) > :actorCount")
    List<Movie> findMoviesWithMoreThanXActors(@Param("actorCount") int actorCount);
}
Usage:
java
List<Movie> popularMovies = movieRepository.findMoviesWithMoreThanXActors(3);
7. Customizing an Individual Repository
Goal: Add custom logic to a single repository beyond query methods.
Steps:
Create a custom interface: MovieRepositoryCustom.
Define a method: List<Movie> findTopRatedMovies();.
Implement it: MovieRepositoryImpl with @PersistenceContext for EntityManager.
Code:
java
public interface MovieRepositoryCustom {
    List<Movie> findTopRatedMovies();
}

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {}

public class MovieRepositoryImpl implements MovieRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Movie> findTopRatedMovies() {
        return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class)
                 .setMaxResults(5)
                 .getResultList();
    }
}
8. Customizing All Repositories
Goal: Apply a common customization across all repositories (e.g., soft deletes).
Steps:
Create a base repository interface: CustomJpaRepository.
Extend JpaRepository and add a custom method.
Implement it with SimpleJpaRepository customization.
Code:
java
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {
    void softDelete(ID id);
}

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> {
    private final EntityManager em;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }

    @Override
    public void softDelete(ID id) {
        T entity = findById(id).orElseThrow();
        em.createQuery("UPDATE " + getDomainClass().getSimpleName() + " e SET e.deleted = true WHERE e.id = :id")
          .setParameter("id", id)
          .executeUpdate();
    }
}
Configuration:
java
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
Usage: Apply to MovieRepository, ActorRepository, etc.
Conclusion
Recap: Spring Data JPA eliminates JDBC boilerplate by providing stock repositories, query methods, and customization options.
Next Steps: Explore Spring Data REST, auditing, or advanced query optimization.
Resources: Link to full code on GitHub (if you host it), Spring Data JPA docs, and the Movie example project.
This outline provides a clear progression from setup to advanced customization, using the Movie, Actor, and Studio example to tie concepts together. You can expand each section with detailed code snippets, screenshots, or video walkthroughs depending on your tutorial format. Let me know if you’d like to refine any part further!