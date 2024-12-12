### Creating and Using a Persistence Unit

# JDBC with MariaDB for Movie DB

## Objective
Master the use of JDBC to interact with MariaDB databases, understand persistence concepts, and implement basic CRUD operations for the Movie DB.

---

## 1. Creating and Using a Persistence Unit

### What is a Persistence Unit?
- **Definition**: A persistence unit acts as a bridge between your application and the database.
- **Purpose**: To manage connections and configurations for interacting with the database.

### Setting Up JDBC Connection
- **Requirements**:
  - JDBC driver for MariaDB.
  - Connection URL, username, and password configuration.

#### Example:
```java
String url = "jdbc:mariadb://localhost:3306/MovieDB";
String user = "yourUsername";
String password = "yourPassword";
Connection conn = DriverManager.getConnection(url, user, password);
```

### Activity
Students configure their own persistence unit by setting up a JDBC connection to MariaDB.

---

## 2. Designing the Database Tables

### Database Schema Design
- **Recap**: Review the ERD (Entity-Relationship Diagram) for Movie DB.
- **Manual Table Creation**: SQL statements to create tables without JPA for automatic schema generation.

### Discussion
- **Topic**: Why might one choose to design tables manually versus relying on ORM tools?

### Hands-On
Write SQL scripts to create the necessary tables for:
- `Studio`
- `Actor`
- `Movie`
- `MovieActor`

---

## 3. Understanding Persistence Scope

### Transaction Scope
- **Definition**: A transaction groups multiple operations into one atomic operation.
- **ACID Properties**: Atomicity, Consistency, Isolation, Durability.

### Persistence Context
- **Introduction**: Explore persistence context in JPA and its JDBC equivalent (connection scope).

#### Practical Example:
```java
try {
    conn.setAutoCommit(false);
    // CRUD operations here
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    throw new RuntimeException(e);
} finally {
    conn.setAutoCommit(true);
}
```

### Quiz
Test students' understanding of when to use transactions and the implications of transaction scope.

---

## 4. Creating the Persistence Configuration

### JDBC Configuration Files
- Discuss the use of configuration files or properties for database settings.

### Connection Pooling
- **Benefits**: Efficient management of database connections.
- **Implementation**: Setup connection pooling using JDBC.

#### Example:
```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:mariadb://localhost:3306/MovieDB");
config.setUsername("yourUsername");
config.setPassword("yourPassword");
HikariDataSource ds = new HikariDataSource(config);
```

### Task
Students configure connection pooling in their project.

---

## 5. Using the Persistence API (JDBC)

### CRUD Operations with JDBC
- **Create**: Insert records.
- **Read**: Query data (including prepared statements).
- **Update**: Modify existing records.
- **Delete**: Remove records.

#### Examples:

**Create:**
```java
PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Studio (StudioName) VALUES (?)");
pstmt.setString(1, "New Studio");
pstmt.executeUpdate();
```

**Read:**
```java
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Actor WHERE ActorName = ?");
pstmt.setString(1, "Tom Hanks");
ResultSet rs = pstmt.executeQuery();
while(rs.next()){
    System.out.println(rs.getString("ActorName"));
}
```

**Update:**
```java
PreparedStatement pstmt = conn.prepareStatement("UPDATE Movie SET MovieRating = ? WHERE MovieId = ?");
pstmt.setDouble(1, 8.5);
pstmt.setInt(2, 123);
pstmt.executeUpdate();
```

**Delete:**
```java
PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Movie WHERE MovieTitle = ?");
pstmt.setString(1, "Old Movie Title");
pstmt.executeUpdate();
```

### Project
Students implement a simple CRUD application using JDBC for the Movie DB, focusing on one entity (like `Actor` or `Studio`).

---

## Engagement Strategies

1. **Live Coding**: Demonstrate setting up JDBC connections, transactions, and CRUD operations in real-time.
2. **Error Handling**: Discuss common JDBC errors and how to resolve them.
3. **Peer Review**: Students review each other's CRUD implementations for:
   - Efficiency
   - Security (e.g., SQL injection prevention)
   - Best practices
4. **Database Design Challenge**: Design an additional table or relationship for the Movie DB, implementing it using JDBC.

---

## Summary
This section provides a solid foundation in using JDBC with MariaDB, offering students insight into data persistence at a lower level. These skills complement or precede learning JPA for a higher level of abstraction.
```