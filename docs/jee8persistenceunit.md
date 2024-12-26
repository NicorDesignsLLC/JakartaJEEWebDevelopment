### Creating and Using a Persistence Unit

**Persistence scope** refers to the lifecycle and context in which JPA entities are managed by the **persistence context**—a part of the EntityManager in JPA. Understanding persistence scope is key to managing entity states, database operations, and the relationships in your Movie, Actor, and Studio entities.

---

### **What is Persistence Context?**

- The **persistence context** is a container that holds and manages entities in memory while interacting with the database.
- It ensures that each entity instance corresponds to one unique row in the database during its lifecycle.

---

### **Persistence Scope**

The scope of persistence determines **how long an entity instance remains associated with the persistence context** and how it is managed. It is typically tied to the **EntityManager** or a **transaction**.

---

### **Types of Persistence Scope**

1. **Transaction Scope**:
    
    - The persistence context is active only during a single transaction.
    - After the transaction ends (commit or rollback), the persistence context is cleared, and entities are detached.
    
    Example in your project:
    
    - When saving a `Movie` entity:
        
        ```java
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setGenre("Sci-Fi");
        em.persist(movie); // Movie is now in the persistence context.
        
        em.getTransaction().commit(); // Persistence context ends here.
        em.close();
        ```
        
    - After `commit()`, the `movie` entity becomes detached, meaning changes to it will no longer be automatically synchronized with the database.
        
2. **Extended Scope**:
    
    - The persistence context spans multiple transactions.
    - This is useful in stateful applications, such as web apps with long user sessions (e.g., using `@PersistenceContext(type = PersistenceContextType.EXTENDED)`).

---

### **Persistence Context States**

Entities in JPA can be in one of the following states within the persistence scope:

#### 1. **Transient**

- Entities that are not yet managed by the persistence context.
- Example: A newly created instance of `Studio` before calling `persist()`:
    
    ```java
    Studio studio = new Studio();
    studio.setStudioName("Warner Bros");
    // This entity is transient and not linked to the database.
    ```
    

#### 2. **Persistent**

- Entities that are managed by the persistence context.
- Changes to these entities are tracked and synchronized with the database during transactions.
- Example: Persisting the `studio`:
    
    ```java
    em.persist(studio);
    // Now the studio is persistent and linked to the database.
    ```
    

#### 3. **Detached**

- Entities that were once managed but are no longer associated with the persistence context.
- Modifications to detached entities must be explicitly merged to synchronize with the database.
- Example:
    
    ```java
    em.close();
    studio.setStudioName("New Warner Bros"); // Detached entity.
    em.merge(studio); // Reattach to persistence context and update the database.
    ```
    

#### 4. **Removed**

- Entities marked for deletion from the database.
- Example:
    
    ```java
    em.remove(studio); // Mark the entity for removal from the database.
    ```
    

---

### **How Persistence Scope Applies to Your Entities**

1. **Studio ↔ Movie (One-to-Many)**:
    
    - When a `Studio` is persisted, all associated `Movie` entities in the `movies` list are also persisted (due to `CascadeType.ALL`).
    - The persistence scope ensures all `Movie` entities remain consistent within the transaction.
    
    Example:
    
    ```java
    Studio studio = new Studio();
    studio.setStudioName("Pixar");
    
    Movie movie = new Movie();
    movie.setTitle("Toy Story");
    movie.setStudio(studio);
    
    em.persist(studio); // Also persists the associated movie due to cascading.
    ```
    
2. **Movie ↔ Actor (Many-to-Many)**:
    
    - When adding an `Actor` to a `Movie`, ensure both entities are within the same persistence context to manage the `Movie_Actor` relationship table.
    
    Example:
    
    ```java
    Actor actor = new Actor();
    actor.setName("Leonardo DiCaprio");
    
    Movie movie = new Movie();
    movie.setTitle("Inception");
    
    movie.getActors().add(actor);
    actor.getMovies().add(movie);
    
    em.persist(movie); // Automatically updates the Movie_Actor join table.
    ```
    
3. **Entity Detachment**:
    
    - If a `Movie` entity is detached after the transaction, changes made to it won’t affect the database until merged:
        
        ```java
        movie.setTitle("Inception 2"); // Detached entity.
        em.merge(movie); // Updates the database with the new title.
        ```
        

---

### **Best Practices for Managing Persistence Scope**

1. **Use Transaction Scope for Stateless Applications**:
    
    - For most web applications, transaction scope is sufficient. Each request should start and end a new transaction.
2. **Minimize Detached State**:
    
    - Avoid making changes to detached entities. Instead, keep entities within the persistence context until all changes are committed.
3. **Leverage Cascading**:
    
    - Use `CascadeType.ALL` or appropriate cascading types for parent-child relationships (e.g., `Studio` and `Movie`) to simplify persistence.
4. **Fetch Only What You Need**:
    
    - Use `FetchType.LAZY` for collections (e.g., `movies` in `Studio`) to prevent loading all related entities unless necessary.

---

### **Key Takeaway**

The **persistence scope** determines how long entities like `Movie`, `Actor`, and `Studio` are managed by JPA. Understanding transient, persistent, detached, and removed states is essential for effectively managing database interactions in your Movie DB application.```