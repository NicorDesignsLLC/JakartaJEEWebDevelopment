## Creating a new JEE 8 Web Application using Maven Archetypes

### 1. Create a new Eclipse project using parent-pom from mojo pom-root archetype

#### Check in into Github using Eclipse (Understand Token Generation process)

#### [Github two factor Authentication with Eclipse](https://stackoverflow.com/questions/32527522/how-to-github-two-factor-authentication-with-eclipse)
 
#### [JEE 8 Webapp Git Start Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/pom-root-start)

### 2. Create a child module inside the POM module using:
 
#### [Maven Archetype Webapp 1.4](https://mvnrepository.com/artifact/org.apache.maven.archetypes/maven-archetype-webapp version 1.4)
 
##### Group Id :  com.nicordesigns

##### Artifact Id : jee8webarchetype

##### Package : com.nicordesigns.jee8webarchetype

### 3. Update the generated module to JEE8 in Eclipse

##### Show Eclipse Problem View - Go through listed Problems and resolve them

##### Upgrade to Java 1.8

##### Add the following Maven Dependency:
 
	<dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
    </dependency>

##### Update the Maven Project using the Eclipse option

### 4. Update web.xml and all related Eclipse Facets

##### Add Tomcat 9 runtime in Facet

##### Demonstrate that the JSP Hello Page Show

### 5. Finally go through and resolve all the warnings in the Eclipse Problem View

#### 6. Check in the end git branch of this slide show

#### [JEE 8 Webapp Git Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/pom-root-finish)
 


    
