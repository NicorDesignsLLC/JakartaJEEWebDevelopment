## ✅ Spring Data’s Unified Data Access

Spring Data JPA offers a consistent and powerful API for accessing and querying data. Once your repositories are configured, you can take advantage of advanced capabilities like:

1. **Sorting**
2. **Pagination**
3. **DTO-based Projections**
4. **Dynamic Queries (via `@Query`)**
5. **Optional: Specifications / Query by Example (QBE)**

This section builds on your existing `CrudRepository` and `JpaRepository` interfaces using real examples from the Movie-Actor-Studio model.

---

### 📌 **1. Sorting Results**

Spring Data allows you to pass a `Sort` parameter directly into query methods:

```java
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByGenre(String genre, Sort sort);
}
```

#### Example Usage

```java
Sort sort = Sort.by(Sort.Direction.DESC, "releaseDate");
List<Movie> movies = movieRepository.findByGenre("Sci-Fi", sort);
```

---

### 📌 **2. Pagination Support**

You can use `PagingAndSortingRepository` or extend `JpaRepository` to get pagination:

```java
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    Page<Movie> findByGenre(String genre, Pageable pageable);
}
```

#### Example Usage

```java
Pageable pageable = PageRequest.of(0, 5, Sort.by("releaseDate").descending());
Page<Movie> page = movieRepository.findByGenre("Action", pageable);
List<Movie> movies = page.getContent();
```

> ✅ `Pageable` and `Page` work seamlessly with Spring MVC controllers as well.

---

### 📌 **3. Projections with DTOs**

You can fetch flattened data using **DTO projections**, either via JPQL or Spring Data’s interface-based projection mechanism.

#### DTO Class

```java
public class MovieSummaryDTO {
    private String title;
    private String studioName;

    public MovieSummaryDTO(String title, String studioName) {
        this.title = title;
        this.studioName = studioName;
    }

    // Getters
}
```

#### JPQL-based Projection

```java
@Query("SELECT new com.nicordesigns.dto.MovieSummaryDTO(m.title, m.studio.studioName) FROM Movie m")
List<MovieSummaryDTO> fetchMovieSummaries();
```

---

### 📌 **4. Custom JPQL & Native Queries**

You’re not limited to derived method names. You can write expressive JPQL or even native SQL if needed:

```java
@Query("SELECT m FROM Movie m WHERE YEAR(m.releaseDate) = ?1")
List<Movie> findByReleaseYear(int year);

@Query(value = "SELECT * FROM movie WHERE duration > ?1", nativeQuery = true)
List<Movie> findMoviesLongerThan(int minutes);
```

---

### 📌 **5. Optional: Query by Example (QBE)**

For dynamic filter criteria, QBE can be powerful:

```java
Example<Movie> example = Example.of(new Movie(null, "Avatar", null, 0, null, null, null, null));
List<Movie> results = movieRepository.findAll(example);
```

---

### ✅ Summary

Spring Data's unified approach gives you:

- Sorting and paging with minimal effort
- DTO projections for frontend-friendly shapes
- JPQL and native queries for complex data needs
- Optional pattern matching using Query-by-Example


