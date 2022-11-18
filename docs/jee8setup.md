## Maven 3 - Eclipse Configuration and setup


#### Starting with our Github created project that we imported into Eclipse in one of our previous lessons 

### 1. Create a new Eclipse project using parent-pom from mojo pom-root archetype
[https://stackoverflow.com/questions/6328778/how-to-create-an-empty-multi-module-maven-project](https://stackoverflow.com/questions/6328778/how-to-create-an-empty-multi-module-maven-project)
Move the generated POM to our original project and run mvn clean package

Convert to Maven Project

Use locally installed Maven version

Update settings.xml according to your work-place requirements

 
##### [JEE 8 Webapp Git Start Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/pom-root-start)

##### [Github two factor Authentication with Eclipse](https://stackoverflow.com/questions/32527522/how-to-github-two-factor-authentication-with-eclipse) (Understand Token Generation process)

#### 2. Create a new sub-module inside the POM-ROOT module using:
 
##### [Maven Archetype webapp-javaee7 ](https://mvnrepository.com/artifact/org.codehaus.mojo.archetypes/webapp-javaee7)
 
## Maven 3 - Eclipse Configuration and setup - END


## Our Basic Jakarta JEE 8 Web Application Structure - START

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
 


    
