# Data Management with Spring, OpenJDK 11, and Jakarta EE 8

## 1. Using Spring Repositories and Transactions

### Objective
Introduce Spring Data repositories and transaction management for efficient data access and consistency.

### Example

```java
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @EntityGraph(attributePaths = {"actors", "studio"})
    List<Movie> findAll();
    
    List<Movie> findByTitleContaining(String titleFragment);
    
    @Query("SELECT m FROM Movie m WHERE m.studio.studioName = ?1")
    List<Movie> findByStudioName(String studioName);
}
```

```java
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void updateMovieTitle(Long id, String newTitle) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(newTitle);
        // Changes are persisted automatically due to @Transactional
    }
}
```

### Explanation
- The `MovieRepository` includes a derived query method (`findByTitleContaining`) and a custom `@Query` to find movies by studio name.
- The `@EntityGraph(attributePaths = {"actors", "studio"})` ensures actors and studio are fetched eagerly.
- The `@Transactional` annotation ensures the update operation is atomic.

---

## 2. Configuring Persistence with the Spring Framework

### Configuration (`persistence.xml`)

```xml
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1">
    <persistence-unit name="MovieDBPU" transaction-type="RESOURCE_LOCAL">
        <class>com.nicordesigns.entities.Movie</class>
        <class>com.nicordesigns.entities.Actor</class>
        <class>com.nicordesigns.entities.Studio</class>
        
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.mariadb.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:mariadb://localhost:3306/SpringJpa"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="123"/>
            
            <!-- Hibernate options -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MariaDBDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

### Explanation
- This configuration sets up a MariaDB database for your movie-related entities (`Studio`, `Movie`, `Actor`), aligning with Jakarta EE 8 JPA requirements.
- The `persistence-unit` defines the necessary JPA configurations for resource-local transactions.

---

## 3. Creating and Using JPA Repositories

### Example

```java
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private int duration;
    private String genre;
    private Rating rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MovieStudioId")
    private Studio studio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Movie_Actor",
        joinColumns = @JoinColumn(name = "MovieId"),
        inverseJoinColumns = @JoinColumn(name = "ActorId")
    )
    private List<Actor> actors = new ArrayList<>();
}
```

```java
@Entity
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studioId;
    private String studioName;
    private Year yearFounded;
    private String studioHeadQuarters;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL)
    private List<Movie> movies = new ArrayList<>();
}
```

```java
@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;
    private String actorName;
    private Date actorBirthDate;
    private String actorNationality;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();
}
```

### Explanation
- The `Movie` entity has a `@ManyToOne` relationship with `Studio` and a `@ManyToMany` relationship with `Actor`.
- The `@EntityGraph` ensures relationships are eagerly loaded when needed while keeping default fetch types as `LAZY`.

---

## 4. Converting Data with DTOs and Entities

### Example

```java
public class MovieDTO {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private int duration;
    private String genre;
    private Rating rating;
    private String studioName;
    private List<String> actorNames;
    
    // Constructors, getters, setters
}
```

```java
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
        dto.setStudioName(movie.getStudio() != null ? movie.getStudio().getStudioName() : "Unknown Studio");
        dto.setActorNames(movie.getActors() != null
                ? movie.getActors().stream().map(Actor::getActorName).collect(Collectors.toList())
                : List.of());
        return dto;
    }
}
```

### Explanation
- The `MovieDTO` flattens the entity relationships (e.g., `Studio` and `Actor`) into simple fields like `studioName` and `actorNames`.
- The `MovieService` handles conversion between `Movie` entities and `MovieDTO` using `ModelMapper`, ensuring actors and studio are fully initialized.
- The `@Transactional(readOnly = true)` in `getAllMovies()` keeps the session open while fetching movies, avoiding `LazyInitializationException`.

---

## Notes on Integration

### Entity Relationships
- `Studio → Movie` as `@OneToMany`
- `Movie ↔ Actor` as `@ManyToMany`, consistent with the provided database design.

### OpenJDK 11 & Jakarta EE 8
- The examples use JPA 2.2 annotations (part of Jakarta EE 8) and are compatible with OpenJDK 11’s runtime.

### DTO Simplification
- The `MovieDTO` avoids exposing full `Studio` or `Actor` objects, aligning with your database structure.

### Performance Optimizations
- `@EntityGraph(attributePaths = {"actors", "studio"})` ensures relationships are loaded efficiently.
- `@Transactional(readOnly = true)` prevents `LazyInitializationException` while keeping transactions read-only for better performance.

