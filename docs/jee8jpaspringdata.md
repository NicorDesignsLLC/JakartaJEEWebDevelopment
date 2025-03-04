# Data Management with Spring, OpenJDK 11, and Jakarta EE 8

## 1. Using Spring Repositories and Transactions

### Objective
Introduce Spring Data repositories and transaction management for efficient data access and consistency.

### Example
```java
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByMovieTitleContaining(String titleFragment);
    
    @Query("SELECT m FROM Movie m WHERE m.studio.studioName = ?1")
    List<Movie> findByStudioName(String studioName);
}

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void updateMovieTitle(Long id, String newTitle) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setMovieTitle(newTitle);
        // Changes are persisted automatically due to @Transactional
    }
}
```

### Explanation
- The `MovieRepository` includes a derived query method (`findByMovieTitleContaining`) and a custom `@Query` to find movies by studio name.
- The `@Transactional` annotation ensures the update operation is atomic.

---

## 2. Configuring Persistence with the Spring Framework

### Reworked Configuration (`persistence.xml`)
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
- This configuration sets up a MariaDB database for your movie-related entities (Studio, Movie, Actor), aligning with Jakarta EE 8 JPA requirements.
- The `persistence-unit` defines the necessary JPA configurations for resource-local transactions.

---

## 3. Creating and Using JPA Repositories

### Reworked Example
```java
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String movieTitle;
    private Date movieReleaseDate;
    private int movieDuration;
    private String movieGenre;
    private String movieRating;

    @ManyToOne
    @JoinColumn(name = "MovieStudioId")
    private Studio studio;

    @ManyToMany
    @JoinTable(
        name = "Movie_Actor",
        joinColumns = @JoinColumn(name = "MovieId"),
        inverseJoinColumns = @JoinColumn(name = "ActorId")
    )
    private List<Actor> actors = new ArrayList<>();
}

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

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByStudioStudioId(Long studioId);
    List<Movie> findByActorsActorName(String actorName);
}
```

### Explanation
- The `Movie` entity has a `@ManyToOne` relationship with `Studio` and a `@ManyToMany` relationship with `Actor`.
- The `MovieRepository` provides methods to query movies by studio ID or actor name.

---

## 4. Converting Data with DTOs and Entities

### Reworked Example
```java
public class MovieDTO {
    private Long movieId;
    private String movieTitle;
    private Date movieReleaseDate;
    private int movieDuration;
    private String movieGenre;
    private String movieRating;
    private String studioName;
    private List<String> actorNames;

    // Constructors, getters, setters
}

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
        MovieDTO dto = modelMapper.map(movie, MovieDTO.class);
        dto.setStudioName(movie.getStudio().getStudioName());
        dto.setActorNames(movie.getActors().stream()
            .map(Actor::getActorName)
            .collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public MovieDTO createMovie(MovieDTO dto) {
        Movie movie = modelMapper.map(dto, Movie.class);
        // Assume Studio and Actors are set separately or fetched by name
        Studio studio = new Studio();
        studio.setStudioName(dto.getStudioName());
        movie.setStudio(studio);
        movie = movieRepository.save(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }
}
```

### Explanation
- The `MovieDTO` flattens the entity relationships (e.g., `Studio` and `Actor`) into simple fields like `studioName` and `actorNames`.
- The `MovieService` handles conversion between `Movie` entities and `MovieDTO` using `ModelMapper`, with custom logic to populate related fields.

---

## Notes on Integration

### Entity Relationships
- `Studio → Movie` as `@OneToMany` and `Movie ↔ Actor` as `@ManyToMany`, consistent with the provided database design.

### OpenJDK 11 & Jakarta EE 8
- The examples use JPA 2.2 annotations (part of Jakarta EE 8) and are compatible with OpenJDK 11’s runtime.

### DTO Simplification
- The `MovieDTO` avoids exposing full `Studio` or `Actor` objects, aligning with your database structure.

