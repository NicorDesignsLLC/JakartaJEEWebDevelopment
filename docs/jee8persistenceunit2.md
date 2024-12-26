### Persistence in Tomcat 9 servlets


When using **Servlets** in a **Tomcat 9** web server with **Java 11**, the **persistence context** must be carefully managed because Servlets operate in a stateless and request-response model. You will need to integrate **JPA** into your web application and ensure proper handling of the persistence scope during each request and transaction. Below is a step-by-step explanation and configuration approach:

---

## **1. Setting Up JPA in a Tomcat Servlet-Based Application**

### **Project Structure**

Your project should include:

- **JPA entities** (`Movie`, `Actor`, `Studio`).
- A **Persistence Unit** defined in `persistence.xml`.
- Servlets to handle HTTP requests.

---

### **2. Configure `persistence.xml`**

Place the `persistence.xml` file in the `META-INF` folder of your project’s `src/main/resources`. This configuration defines the **persistence unit**, database connection, and properties for JPA.

**Example: `persistence.xml`:**

```xml
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1">
    <persistence-unit name="MovieDBPU" transaction-type="RESOURCE_LOCAL">
        <class>com.nicordesigns.entities.Movie</class>
        <class>com.nicordesigns.entities.Actor</class>
        <class>com.nicordesigns.entities.Studio</class>
        
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.mariadb.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:mariadb://localhost:3306/SpringJpa"/>
            <property name="javax.persistence.jdbc.user" value="your_db_user"/>
            <property name="javax.persistence.jdbc.password" value="your_db_password"/>
            
            <!-- Hibernate options -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.MariaDBDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

- **Key points:**
    - The persistence unit `MovieDBPU` will be referenced in your code.
    - `RESOURCE_LOCAL` transaction type is used for standalone JPA with a manual transaction boundary.

---

## **3. Dependency Management**

Add the necessary dependencies for JPA, Hibernate, and the MariaDB connector in your `pom.xml` file:

**Maven Dependencies:**

```xml
<dependency>
    <groupId>javax.persistence</groupId>
    <artifactId>javax.persistence-api</artifactId>
    <version>2.2</version>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.4.33.Final</version>
</dependency>
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <version>2.7.4</version>
</dependency>
```

---

## **4. Servlet Configuration**

### **Using `EntityManager` in a Servlet**

The `EntityManager` must be properly managed for each HTTP request. A common pattern is to create and close the `EntityManager` within the lifecycle of a request.

**Example Servlet:**

```java
@WebServlet(name = "MovieServlet", urlPatterns = {"/movies"})
public class MovieServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("MovieDBPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        try {
            // Fetch all movies
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();

            // Write movies as JSON (example)
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("[");
            for (Movie movie : movies) {
                out.println("{\"id\": \"" + movie.getId() + "\", \"title\": \"" + movie.getTitle() + "\"},");
            }
            out.println("]");
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}
```

- **Key points:**
    - The `EntityManagerFactory` is initialized once in the `init()` method and closed in `destroy()`.
    - A new `EntityManager` is created and closed for each HTTP request to ensure proper persistence scope management.

---

## **5. Transaction Management**

In a **Servlet-based application**, transactions must be manually managed using the `EntityTransaction` API.

**Example: Saving a Movie:**

```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();

    try {
        transaction.begin();

        // Create and persist a new Movie
        Movie movie = new Movie();
        movie.setTitle(request.getParameter("title"));
        movie.setGenre(request.getParameter("genre"));
        movie.setDuration(Integer.parseInt(request.getParameter("duration")));

        em.persist(movie);

        transaction.commit();
        response.getWriter().println("Movie saved successfully!");

    } catch (Exception e) {
        if (transaction.isActive()) {
            transaction.rollback();
        }
        response.getWriter().println("Error saving movie: " + e.getMessage());
    } finally {
        em.close();
    }
}
```

---

## **6. Handling Persistence Scope**

### **For Each Request:**

- **Transient State:** A new entity instance is created in the Servlet code.
- **Persistent State:** When `em.persist(entity)` is called, the entity becomes part of the persistence context.
- **Transaction Boundary:** The `EntityTransaction` ensures changes are committed to the database within the request scope.
- **Detached State:** After the `EntityManager` is closed, the entity becomes detached, and subsequent changes must be merged.

---

## **7. Configuring Tomcat for Deployment**

### **Deploy the WAR File**

- Package your project into a WAR file using Maven:
    
    ```bash
    mvn clean package
    ```
    
- Deploy the WAR to the `webapps` folder of Tomcat 9:
    
    ```
    cp target/MovieDBApp.war /path/to/tomcat/webapps/
    ```
    

### **Datasource Configuration (Optional)**

If you want to use a managed datasource, configure it in `META-INF/context.xml`:

```xml
<Resource name="jdbc/MovieDB" auth="Container" type="javax.sql.DataSource"
          driverClassName="org.mariadb.jdbc.Driver"
          url="jdbc:mariadb://localhost:3306/SpringJpa"
          username="your_db_user" password="your_db_password"
          maxActive="20" maxIdle="10" maxWait="10000"/>
```

---

## **8. Advanced Considerations**

1. **Concurrency**:
    - Each request gets its own `EntityManager` to avoid threading issues.
2. **Error Handling**:
    - Implement error pages and exception mapping for better user experience.
3. **Performance**:
    - Use connection pooling libraries like HikariCP to improve database performance.

By following this approach, you can effectively manage persistence scope and integrate JPA with Servlets in a Tomcat-based web application.