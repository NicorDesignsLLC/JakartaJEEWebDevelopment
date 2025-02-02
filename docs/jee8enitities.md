# **JPA Essentials for the Movie Database**

## **1. Introduction**

### Why Use JPA for Database Operations?
- JPA simplifies the process of interacting with relational databases by abstracting the boilerplate code required for SQL and JDBC.
- With JPA, you can work with database tables as Java objects, making your code cleaner and easier to maintain.

### How JPA Simplifies Our Movie DB Project
- Maps classes like `Movie`, `Actor`, and `Studio` directly to their respective database tables.
- Handles relationships like many-to-many (`Movie ↔ Actor`) and many-to-one (`Movie → Studio`) seamlessly.
- Automatically generates SQL queries for common operations (e.g., save, update, delete).

---

## **2. Maven Dependencies**

### Key Points:
- Learn the basics of dependency management with Maven.
- Add the required dependencies for JPA and MariaDB connectivity.

### Hands-On: Adding Dependencies
Update your `pom.xml` file with the following:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## **3. Marking an Entity and Mapping It to a Table**

### Concepts:
- A **JPA Entity** is a simple Java class that is mapped to a database table.
- Use `@Entity` to mark a class and `@Table` to specify the table name.

### Example: Mapping the `Studio` Class
```java
import javax.persistence.*;

@Entity
@Table(name = "Studio")
public class Studio {
    // Fields and methods here...
}
```

---

## **4. How JPA Uses Entity Fields**

### Key Points:
- JPA automatically maps class fields to database columns by default.
- Use `@Column` to customize the mapping (e.g., renaming, setting constraints).

### Practical Example: Map the `studioName` Field
```java
@Column(name = "StudioName", nullable = false, length = 100)
private String studioName;
```

---

## **5. Mapping Surrogate Fields**

### Explanation:
- Surrogate keys are unique IDs (primary keys) generated independently of the data.
- JPA manages surrogate keys efficiently with annotations like `@Id` and `@GeneratedValue`.

### Demo: Adding a Surrogate Key
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

---

## **6. Creating Simple IDs**

### Teachings:
- Every JPA entity must have an ID field.
- Use `@Id` and `@GeneratedValue` to define and auto-generate unique identifiers.

### Example: Setting Up IDs for the `Actor` Entity
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long actorId;
```

---

## **7. Creating Composite IDs**

### Concept:
- Use composite keys for tables like `Movie_Actor` that combine keys from multiple tables.
- Define a composite key class with `@Embeddable`.

### Example: Composite Key for the `Movie_Actor` Table
```java
@Embeddable
public class MovieActorId implements Serializable {
    @Column(name = "MovieId")
    private Long movieId;
    @Column(name = "ActorId")
    private Long actorId;

    // equals, hashCode, getters, and setters...
}
```

---

## **8. Using Basic Data Types**

### Discussion:
- JPA supports common Java types like `String`, `Integer`, `LocalDate`, and `BigDecimal`.
- Maps them directly to SQL types.

### Illustration: Examples in `Movie` Entity
```java
@Column(name = "MovieReleaseDate")
private LocalDate releaseDate;

@Column(name = "MovieRating", precision = 3, scale = 1)
private BigDecimal rating;
```

---

## **9. Specifying Column Names and Details**

### Key Points:
- Customize columns using `@Column` annotations to set constraints like `nullable`, `length`, and `unique`.

### Example: Customizing the `actorName` Field
```java
@Column(name = "ActorName", nullable = false, length = 100, unique = true)
private String actorName;
```

