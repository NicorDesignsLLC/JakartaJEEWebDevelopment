# Mapping Complex Data Types

## Advanced JDBC and JPA Mapping

### Objective
Deepen understanding of mapping complex data types, using enums, handling dates and times, and managing large properties in the Movie DB using JPA.

---

## 1. Mapping Complex Data Types

### Introduction
- Discuss how complex data types (e.g., lists, maps, embedded objects) are stored in relational databases.
- Explore strategies for mapping these data types in JPA or JDBC.

### Key Concepts
- **Embeddables**: Using `@Embeddable` for reusable complex types.
- **Collections**: Mapping collections like lists or sets with JPA annotations.

### Example: Mapping an Embedded Address
```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipCode;
}
```

```java
@Entity
public class Studio {
    @Id
    private Long id;
    private String name;
    @Embedded
    private Address address;
}
```

### Activity
- Students define a reusable `Address` class and map it to the `Studio` entity in the Movie DB.

---

## 2. Using Enums as Entity Properties

### Introduction
- Explain the purpose of enums in modeling finite state values.
- Discuss enum storage strategies: **ORDINAL** vs. **STRING**.

### Key Concepts
- **@Enumerated**: Annotation for mapping enums to database columns.

### Example: Mapping Movie Ratings as Enums
```java
public enum Rating {
    G, PG, PG_13, R, NC_17
}

@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Rating rating;
}
```

### Hands-On Task
- Students add a `Rating` enum to the `Movie` entity and test saving/retrieving movies with different ratings.

### Discussion
- Pros and cons of using `ORDINAL` vs. `STRING` storage.

---

## 3. Understanding How JPA Handles Dates and Times

### Introduction
- Overview of the `java.time` API for handling dates and times.
- Discuss how JPA maps dates and times to database columns.

### Key Concepts
- **Temporal Types**: Using `@Temporal` to specify the precision (`DATE`, `TIME`, `TIMESTAMP`).
- Native support for `java.time` types in JPA.

### Example: Mapping Movie Release Dates
```java
@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
}
```

### Advanced Example: Using `java.time.LocalDate`
```java
@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    private LocalDate releaseDate;
}
```

### Practical Exercise
- Students add a `releaseDate` field to the `Movie` entity and experiment with different temporal precisions.

### Quiz
- Test understanding of when to use `DATE`, `TIME`, or `TIMESTAMP`.

---

## 4. Mapping Large Properties to CLOBS and BLOBS

### Introduction
- Overview of handling large text (`CLOB`) and binary data (`BLOB`) in JPA.
- Common use cases: storing movie descriptions or images.

### Key Concepts
- **@Lob**: Annotation for large object storage.
- Difference between `@Lob` with `CLOB` and `BLOB`.

### Example: Mapping a Large Movie Description
```java
@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    @Lob
    private String description;
}
```

### Example: Mapping Movie Posters as Binary Data
```java
@Entity
public class Movie {
    @Id
    private Long id;
    private String title;
    @Lob
    private byte[] poster;
}
```

### Hands-On Task
- Students map a large `description` to a `CLOB` and a binary `poster` to a `BLOB` for the `Movie` entity.

### Engagement Strategies
- **Code Review**: Students share their `CLOB` and `BLOB` implementations for peer review.
- **Performance Discussion**: Explore the implications of storing large objects in the database.

---

## Summary
This lesson builds on the foundational JDBC and JPA knowledge, focusing on advanced data mapping techniques. By the end of this section, students will confidently map complex data types, enums, dates/times, and large properties in the Movie DB.
```