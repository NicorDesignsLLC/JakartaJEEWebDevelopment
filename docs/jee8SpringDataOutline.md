### ✅ **1. Updated Maven Dependencies**

Replace your current dependencies with the following:

```xml
<!-- Spring Data JPA (compatible with Jakarta EE 8 and OpenJDK 11) -->
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
    <version>2.7.12</version> <!-- Jakarta EE 8 compatible, Spring 5.3.x aligned -->
</dependency>

        <!-- JPA API - Jakarta EE 8 -->
<dependency>
<groupId>jakarta.persistence</groupId>
<artifactId>jakarta.persistence-api</artifactId>
<version>2.2.3</version> <!-- Aligned with Jakarta EE 8 (JPA 2.2) -->
</dependency>

        <!-- SLF4J API for logging -->
<dependency>
<groupId>org.slf4j</groupId>
<artifactId>slf4j-api</artifactId>
<version>1.7.36</version>
</dependency>

        <!-- SLF4J Simple implementation (or Logback/log4j if preferred) -->
<dependency>
<groupId>org.slf4j</groupId>
<artifactId>slf4j-simple</artifactId>
<version>1.7.36</version>
<scope>runtime</scope>
</dependency>

        <!-- Hibernate Core as JPA provider (compatible with JDK 11 & Jakarta EE 8) -->
<dependency>
<groupId>org.hibernate</groupId>
<artifactId>hibernate-core</artifactId>
<version>5.6.15.Final</version>
</dependency>

        <!-- Optional: MariaDB JDBC Driver -->
<dependency>
<groupId>org.mariadb.jdbc</groupId>
<artifactId>mariadb-java-client</artifactId>
<version>2.7.9</version>
</dependency>
```

> **Note:**
> - `spring-data-jpa:2.7.x` is the last line of Spring Data compatible with Spring Framework 5.3.x and Jakarta EE 8 (before Spring Boot 3.x moves to Jakarta EE 9+).
> - Jakarta EE 8 still uses `jakarta.persistence` with package names like `javax.persistence`, but artifact group IDs are now under `jakarta.*`.

---

### ✅ **2. Configuring and Creating Spring Data Repositories**
[View File](jee8jpaspringdata.md)

### ✅ **3. Spring Data's Unified Data Access**
[View File](jee8boilerjdbc.md)

### ✅ **4. Refactoring our Charity Registration Application**
[View File](jee8SpringDataCharity.md)

