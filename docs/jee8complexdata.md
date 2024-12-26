# Mapping Complex Data Types

# **Advanced JDBC and JPA Mapping**

## **Objective**

Gain advanced skills in mapping complex data types, enums, dates/times, and large properties using JPA while working with the Movie DB. By the end of this lesson, you will confidently implement advanced mappings and understand their use cases in real-world applications.

---

## **1. Mapping Complex Data Types**

### **Introduction**
- Relational databases don’t natively support complex data types like lists or embedded objects. JPA provides strategies to map these structures effectively.
- In this section, you’ll learn to:
  - Use `@Embeddable` for reusable components like `Address`.
  - Map collections (e.g., `List`, `Set`) to database tables.

### **Key Concepts**
- **Embeddables**: Reusable, non-entity types annotated with `@Embeddable`.
- **Collections**: Use `@ElementCollection` for lists and sets.

### **Example: Mapping an Embedded Address**
1. Create an `Address` class for use in multiple entities:
   ```java
   @Embeddable
   public class Address {
       private String street;
       private String city;
       private String zipCode;

       // Getters and Setters
   }
   ```

2. Embed the `Address` in the `Studio` entity:
   ```java
   @Entity
   public class Studio {
       @Id
       private Long id;

       private String name;

       @Embedded
       private Address address;

       // Getters and Setters
   }
   ```

### **Activity**
- Define an `Address` class and add it to the `Studio` entity. Test saving a `Studio` with an embedded address.

---

## **2. Using Enums as Entity Types**

### **Introduction**
- Enums simplify managing finite values like ratings or statuses.
- JPA supports two storage strategies:
  - **ORDINAL**: Stores the position (e.g., `0` for `G`, `1` for `PG`).
  - **STRING**: Stores the name (e.g., `G`, `PG`).

### **Key Concepts**
- Use `@Enumerated` to map enums to database columns.
- Prefer `EnumType.STRING` for readability and future-proofing.

### **Example: Mapping Movie Ratings**
1. Define an enum for `Rating`:
   ```java
   public enum Rating {
       G, PG, PG_13, R, NC_17
   }
   ```

2. Add the `Rating` enum to the `Movie` entity:
   ```java
   @Entity
   public class Movie {
       @Id
       private Long id;

       private String title;

       @Enumerated(EnumType.STRING)
       private Rating rating;

       // Getters and Setters
   }
   ```

### **Hands-On Task**
- Add a `Rating` field to the `Movie` entity, map it with `@Enumerated(EnumType.STRING)`, and test saving and retrieving movies with different ratings.

### **Discussion**
- Compare `ORDINAL` and `STRING` strategies:
  - **ORDINAL**: More compact but fragile if the enum changes.
  - **STRING**: Human-readable and more stable for schema evolution.

---

## **3. Understanding How JPA Handles Dates and Times**

### **Introduction**
- The `java.time` API is the modern standard for handling dates and times in Java.
- JPA supports mapping dates and times to database columns with precise control.

### **Key Concepts**
- Use `@Temporal` for legacy `Date` or `Calendar` types.
- Directly map `java.time.LocalDate`, `LocalDateTime`, and other modern types.

### **Examples**
1. **Using Legacy Date Types**:
   ```java
   @Entity
   public class Movie {
       @Id
       private Long id;

       @Temporal(TemporalType.DATE)
       private Date releaseDate;

       // Getters and Setters
   }
   ```

2. **Using `LocalDate`**:
   ```java
   @Entity
   public class Movie {
       @Id
       private Long id;

       private LocalDate releaseDate;

       // Getters and Setters
   }
   ```

### **Practical Exercise**
- Add a `releaseDate` field to the `Movie` entity and experiment with saving and querying movies with different date formats.

### **Quiz**
- What is the difference between `DATE`, `TIME`, and `TIMESTAMP` in JPA?

---

## **4. Mapping Large Properties to CLOBS and BLOBS**

### **Introduction**
- Large text and binary data are common in applications (e.g., movie descriptions, posters).
- JPA provides the `@Lob` annotation to handle these properties.

### **Key Concepts**
- **CLOB (Character Large Object)**: For storing large text (e.g., movie descriptions).
- **BLOB (Binary Large Object)**: For storing binary data (e.g., images, files).

### **Examples**
1. **Mapping a Movie Description (CLOB)**:
   ```java
   @Entity
   public class Movie {
       @Id
       private Long id;

       private String title;

       @Lob
       private String description;

       // Getters and Setters
   }
   ```

2. **Mapping Movie Posters (BLOB)**:
   ```java
   @Entity
   public class Movie {
       @Id
       private Long id;

       @Lob
       private byte[] poster;

       // Getters and Setters
   }
   ```

### **Hands-On Task**
- Add a large `description` field as a `CLOB` and a `poster` field as a `BLOB` to the `Movie` entity. Test saving and retrieving large objects.

### **Engagement Strategies**
1. **Code Review**:
   - Pair up with another student and review each other's `CLOB` and `BLOB` implementations.
2. **Performance Discussion**:
   - Discuss the performance implications of storing large objects in the database vs. using external storage (e.g., AWS S3).

---

## **Summary**

This lesson explored advanced JPA techniques, including mapping complex types, enums, dates/times, and large objects. By applying these concepts, you can now:
- Handle complex relationships and reusable components.
- Use enums effectively for finite state modeling.
- Manage dates and times with precision.
- Store and retrieve large objects like text and images in the database.
