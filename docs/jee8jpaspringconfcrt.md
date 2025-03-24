## ✅ Configuring and Creating Spring Data Repositories

This section demonstrates how to eliminate DAO boilerplate using **Spring Data JPA**'s `CrudRepository`. You’ll see how to configure persistence, define real repositories, and use them in services — all with your **Movie, Actor, Studio** domain model.

---

### 🔧 1. Spring JPA Configuration (Jakarta EE 8 Compatible)

```java
@Configuration
@EnableJpaRepositories(basePackages = "com.nicordesigns.repository")
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.nicordesigns.entities");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        emf.setJpaProperties(props);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
```

---

### 📁 2. Define Real `CrudRepository` Interfaces

#### 🎬 MovieRepository

```java
@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String titleFragment);

    @Query("SELECT m FROM Movie m WHERE m.studio.studioName = ?1")
    List<Movie> findByStudioName(String studioName);

    @EntityGraph(attributePaths = {"actors", "studio"})
    @Query("SELECT m FROM Movie m")
    List<Movie> findAllWithActorsAndStudio();
}
```

#### 🏢 StudioRepository

```java
@Repository
public interface StudioRepository extends CrudRepository<Studio, Long> {

    List<Studio> findByStudioNameContainingIgnoreCase(String studioName);

    List<Studio> findByYearFounded(Year year);
}
```

> 💡 `CrudRepository<T, ID>` provides basic persistence operations:  
> `save()`, `findById()`, `findAll()`, `deleteById()`, `count()`, etc.

---

### 🧪 3. Using the Repositories in Service Classes

#### 🎬 MovieService

```java
@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> getAllMoviesWithDetails() {
        return movieRepository.findAllWithActorsAndStudio();
    }

    @Transactional(readOnly = true)
    public List<Movie> searchByTitle(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
```

#### 🏢 StudioService

```java
@Service
public class StudioService {

    @Autowired
    private StudioRepository studioRepository;

    @Transactional
    public Studio createStudio(Studio studio) {
        return studioRepository.save(studio);
    }

    @Transactional(readOnly = true)
    public List<Studio> searchStudiosByName(String keyword) {
        return studioRepository.findByStudioNameContainingIgnoreCase(keyword);
    }

    @Transactional
    public void deleteStudioById(Long id) {
        if (!studioRepository.existsById(id)) {
            throw new IllegalArgumentException("Studio ID not found: " + id);
        }
        studioRepository.deleteById(id);
    }
}
```

---

### ✅ Summary

With `CrudRepository`, you can:
- Rapidly prototype JPA-based apps without writing DAO implementations
- Use **derived queries** and **custom JPQL** to fetch meaningful data
- Load entity relationships using `@EntityGraph` to avoid N+1 issues
- Keep your service layer focused on **business logic and transactions**

---

[Continue to Unified Data Access](jee8udac.md)
