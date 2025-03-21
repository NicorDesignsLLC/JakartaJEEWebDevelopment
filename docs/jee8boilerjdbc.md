## Eliminating Boilerplate JDBC Code with Spring Data JPA**

### **1. Introduction**
- **Recap:** In the previous lesson, we created repositories for our Movie DB and implemented basic CRUD operations using raw JDBC or JPA.
- **Objective:** In this lesson, we’ll explore how Spring Data simplifies data retrieval, filtering, and pagination, reducing boilerplate code.
- **Key Focus Areas:**
    - The problem with traditional JPA approaches.
    - How Spring Data provides a unified and declarative approach.
    - Practical examples using `CrudRepository`, `PagingAndSortingRepository`, and `JpaRepository`.

---

### **2. The Problem: Why Avoid Boilerplate Code?**
- **Code Duplication:** Traditional approaches require writing separate queries for counting and retrieving paginated data.
- **Verbosity:** JPQL and Criteria API require repetitive, complex code.
- **Maintainability:** More code means more maintenance overhead.

#### **Example: Paginating with Raw JPA**
##### **JPQL Approach**
```java
TypedQuery<Long> countQuery = entityManager.createQuery(
    "SELECT count(m) FROM Movie m WHERE predicates...", Long.class);
long totalRows = countQuery.getSingleResult();

TypedQuery<Movie> pagedQuery = entityManager.createQuery(
    "SELECT m FROM Movie m WHERE predicates... ORDER BY m.title ASC, m.releaseYear DESC", 
    Movie.class
);
pagedQuery.setFirstResult(startRecordNumber);
pagedQuery.setMaxResults(maxPerPage);
List<Movie> singlePage = pagedQuery.getResultList();
```

##### **Criteria API Approach**
```java
CriteriaBuilder builder = entityManager.getCriteriaBuilder();

// Count Query
CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
Root<Movie> root = countCriteria.from(Movie.class);
TypedQuery<Long> countQuery = entityManager.createQuery(
    countCriteria.select(builder.count(root)).where(predicates...)
);
long totalRows = countQuery.getSingleResult();

// Paginated Query
CriteriaQuery<Movie> criteria = builder.createQuery(Movie.class);
root = criteria.from(Movie.class);
TypedQuery<Movie> pagedQuery = entityManager.createQuery(
    criteria.select(root)
            .where(predicates...)
            .orderBy(builder.asc(root.get("title")), builder.desc(root.get("releaseYear")))
);
pagedQuery.setFirstResult(startRecordNumber);
pagedQuery.setMaxResults(maxPerPage);
List<Movie> singlePage = pagedQuery.getResultList();
```

#### **Key Issues**
- **Too much code** for something as common as pagination.
- **Requires manual pagination and sorting logic**.
- **Hard to maintain and modify** when queries evolve.

---

### **3. Introducing Spring Data JPA**
- **What is Spring Data JPA?**
    - A high-level abstraction over JPA that simplifies data access.
    - Uses **interfaces** and **method conventions** to auto-generate queries.
    - Provides built-in support for **pagination, sorting, and query derivation**.

- **Core Components**
    - `CrudRepository` → Basic CRUD operations.
    - `PagingAndSortingRepository` → Adds pagination and sorting.
    - `JpaRepository` → Extends both, with additional JPA-specific methods.

---

### **4. Using `CrudRepository` for Basic Data Access**
#### **Definition: A Core Interface for CRUD Operations**
```java
public interface CrudRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);              
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
    Optional<T> findById(ID id);                 
    boolean existsById(ID id);                   
    Iterable<T> findAll();                       
    Iterable<T> findAllById(Iterable<ID> ids);   
    long count();                                
    void deleteById(ID id);                      
    void delete(T entity);                       
    void deleteAll(Iterable<? extends T> entities);
    void deleteAll();                            
}
```

- **How It Works:**
    - No need for an implementation class.
    - Just define an interface extending `CrudRepository<T, ID>`.

#### **Refactoring the Movie Repository**
```java
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String titleFragment);
}
```
- The method **`findByTitleContaining`** is **automatically implemented** by Spring Data JPA!

---

### **5. Simplifying Pagination with `PagingAndSortingRepository`**
- **Adding Pagination Support**
```java
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Page<Movie> findAll(Pageable pageable);
}
```

- **Using Pagination in Service Layer**
```java
Page<Movie> moviePage = movieRepository.findAll(
        PageRequest.of(pageNumber, pageSize, Sort.by("title").ascending())
);
long totalRows = moviePage.getTotalElements();
List<Movie> singlePage = moviePage.getContent();
```

#### **Why Is This Better?**
✅ **No need to write JPQL or Criteria API manually.**  
✅ **Handles both total count and paginated results automatically.**  
✅ **Supports sorting declaratively using `Sort.by()`**.

---

### **6. Going Further with `JpaRepository`**
- `JpaRepository` extends `PagingAndSortingRepository` and provides additional JPA-specific methods.
- **Extending JpaRepository for More Flexibility**
```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String titleFragment);
}
```
- `JpaRepository` provides:
    - **Batch inserts** (`saveAll()`)
    - **Flush methods** (`flush()`, `saveAndFlush()`)
    - **More advanced query derivation support**.

---

### **7. Recap & Next Steps**
#### **Key Takeaways**
✅ **Spring Data JPA eliminates boilerplate CRUD and pagination logic.**  
✅ **`CrudRepository`, `PagingAndSortingRepository`, and `JpaRepository` provide a clean abstraction for data access.**  
✅ **Pagination, sorting, and filtering become simpler with `PageRequest` and `Sort` utilities.**

Sure! Here’s the final section covering **custom queries** in Spring Data JPA.

---

## **8. Writing Custom Queries in Spring Data JPA**
While Spring Data JPA provides **method name conventions** to generate queries automatically, sometimes **custom queries** are needed for more complex filtering or joins.

### **8.1. Query Methods: Deriving Queries by Naming Convention**
Spring Data JPA can **automatically generate queries** based on method names.

#### **Example: Find Movie by Title**
```java
List<Movie> findByTitleContaining(String titleFragment);
```
- **Spring Data JPA generates the following JPQL query automatically:**
```sql
SELECT m FROM Movie m WHERE m.title LIKE %:titleFragment%
```
- No need for an explicit `@Query` annotation!

#### **Example: Find Movies Released After a Given Year**
```java
List<Movie> findByReleaseDateAfter(LocalDate releaseDate);
```
- **Generated JPQL:**
```sql
SELECT m FROM Movie m WHERE m.releaseDate > :releaseDate
```

---

### **8.2. Custom Queries with `@Query`**
For **more control**, we can define custom JPQL or native SQL queries using `@Query`.

#### **Example: Find Movie by Title Using JPQL**
```java
@Query("SELECT m FROM Movie m WHERE m.title = :title")
Movie findMovieByTitle(@Param("title") String title);
```
- The `@Query` annotation allows writing **JPQL** queries explicitly.
- `@Param("title")` binds method parameters to query variables.

---

### **8.3. Custom Queries with JOINs**
Spring Data JPA also supports **joining relationships**.

#### **Example: Find Movies by Actor Name**
```java
@Query("SELECT m FROM Movie m JOIN m.actors a WHERE a.actorName = :actorName")
List<Movie> findMoviesByActor(@Param("actorName") String actorName);
```
- **How it works:**
    - Uses `JOIN` to fetch movies based on the `Actor` entity.
    - Filters by actor's name (`a.actorName`).
    - Returns a list of movies starring the given actor.

---

### **8.4. Native SQL Queries**
If JPQL doesn’t cover your needs, **native SQL** can be used.

#### **Example: Find Movies Using Native SQL**
```java
@Query(value = "SELECT * FROM Movie WHERE title = :title", nativeQuery = true)
Movie findMovieByTitleNative(@Param("title") String title);
```
- `nativeQuery = true` tells Spring Data JPA to execute raw SQL.
- Use when **performance optimizations** require database-specific SQL.

---

### **8.5. Pagination with Custom Queries**
#### **Example: Paginate Movies by Genre**
```java
@Query("SELECT m FROM Movie m WHERE m.genre = :genre")
Page<Movie> findMoviesByGenre(@Param("genre") String genre, Pageable pageable);
```
- `Pageable` allows dynamic pagination.
- Supports sorting and limits results per page.

#### **Usage in Service Layer**
```java
Page<Movie> pagedMovies = movieRepository.findMoviesByGenre("Action", PageRequest.of(0, 10));
List<Movie> movies = pagedMovies.getContent();
```
- **Retrieves the first 10 action movies, ordered by default.**
- **Reduces database load** compared to fetching all movies at once.

---

## **9. Summary**
✅ **Spring Data JPA reduces boilerplate by deriving queries from method names.**  
✅ **Use `@Query` for complex filtering, joins, and native SQL queries.**  
✅ **Pagination is easy with `Pageable` and `findAll(Pageable)`.**

### **Next Steps**
- Implement **dynamic filtering** using **Spring Data Specifications**.
- Learn about **DTO-based projections** to return only needed fields.
- Explore **batch processing and bulk updates** in Spring Data JPA.

---

This addition makes your lesson complete by covering **custom queries, joins, pagination, and native SQL**! 🚀  
Let me know if you want any further refinements.
---


