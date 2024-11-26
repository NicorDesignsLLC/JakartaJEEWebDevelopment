#### **5. Maven Dependencies for Our Sample Module**

- **Core Dependencies**:
  ```xml
  
  
    <dependencies>
      <!-- JPA API -->
      <dependency>
          <groupId>javax.persistence</groupId>
          <artifactId>javax.persistence-api</artifactId>
          <version>2.2</version>
      </dependency>
      
      <!-- Hibernate Core -->
      <dependency>
          <groupId>org.hibernate</groupId>
          <artifactId>hibernate-core</artifactId>
          <version>5.4.35.Final</version>
      </dependency>
      
      <!-- MariaDB JDBC Driver -->
      <dependency>
          <groupId>org.mariadb.jdbc</groupId>
          <artifactId>mariadb-java-client</artifactId>
          <version>2.7.3</version>
      </dependency>
  </dependencies>
  
  
  **Maven Plugin for Schema Generation** (Optional):
  xml
    
	  <build>
	      <plugins>
	          <plugin>
	              <groupId>org.hibernate</groupId>
	              <artifactId>hibernate5-maven-plugin</artifactId>
	              <version>1.0.0</version>
	              <configuration>
	                  <components>
	                      <component>
	                          <name>hbm2ddl</name>
	                          <outputDirectory>src/main/resources/schema</outputDirectory>
	                      </component>
	                  </components>
	              </configuration>
	          </plugin>
	      </plugins>
	  </build>


**How to Add to Your Project**:
  - Place the `pom.xml` in your module.
  - Run:
    ```bash
    mvn clean install
    ```
- **Configure `persistence.xml`**:
  - Add the database connection properties:
    ```xml
    <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1">
        <persistence-unit name="sampleUnit">
            <class>com.example.SampleEntity</class>
            <properties>
                <property name="javax.persistence.jdbc.driver" value="org.mariadb.jdbc.Driver" />
                <property name="javax.persistence.jdbc.url" value="jdbc:mariadb://localhost:3306/sample_db" />
                <property name="javax.persistence.jdbc.user" value="sample_user" />
                <property name="javax.persistence.jdbc.password" value="password" />
                <property name="hibernate.dialect" value="org.hibernate.dialect.MariaDBDialect" />
            </properties>
        </persistence-unit>
    </persistence>
    ```

---