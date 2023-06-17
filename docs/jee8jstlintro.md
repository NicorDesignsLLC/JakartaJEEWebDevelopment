# Introducing JSP Tags and JSTL

- In order to conform to the MVC pattern we want to remove all Java Code from our JSP's , Expression Language is a good start but even here we relied on embedded Java Code in our JSP's
- We will expand on <c:url> and <c:redirect> tags we've seen before and dig deeper into JSTL and JSP Tags
- Client side development in itself has become a specialist domain involving HTML, CSS and JavaScript frameworks

### 1. Working with Tags 

[Jakarta 1.2 Tags Specification](https://jakarta.ee/specifications/tags/1.2/)  

[Jakarta 2.0 Specification Document](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html)

[Oracle Taglibs Documentation](https://docs.oracle.com/cd/B10500_01/java.920/a96657/taglibs.htm)

[Oracle JSTL Technologies](https://www.oracle.com/java/technologies/jstl.html)

[Tomcat Standard Taglibs documentation](https://tomcat.apache.org/taglibs/standard/)


JSP Tags are specified as Actions in the official Specification and each performs a specific action

[How Actions are documented](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#how-actions-are-documented)

We use the taglib directive at the top of our JSP to indicate that we will be using the JSTL Tag libraries

[Jakarta Tag Specification Overview](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#overview-9)


		<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

Note that this URL is not an actual working URL of the TLD but a type of naming convention that are used behind the scenes to locate the the correct TLD by the browser and your IDE. The prefix also follows a convention rather than a configuration.


The location of these TLD files are often a source of confusion and frustration as they can vary according to whatever JEE Compliant Web Server you use.  The order in which the JSP parser locates the TLD files are listed below:

1. The files are located according to the JEE 8 Specification inside the JEE 8 compliant web container

2. The <taglib> declarations within the web.xml <jsp-config> section can also indicate the TLD location in your web project

3. The TLD jar files can also be placed in the /META-INF/WEB-INF/lib directory, where the JSP parser will pick it up

4. In our case Tomcat 9.5 requires you to install JSTL,  which in turn requires you to set up your Eclipse Project Properties to match when you are deploying to your local Tomcat Server to debug and run. 

More on the confusion about JSTL and out Tomcat 9.5 version here:

[JSTL Installation Location](https://stackoverflow.com/questions/4928271/how-to-install-jstl-the-absolute-uri-http-java-sun-com-jstl-core-cannot-be-r)


More on taglib directives here: 

[Oracle JEE 5 Taglib Directives Tutorial](https://docs.oracle.com/javaee/5/tutorial/doc/bnamu.html)


We will be looking a the following 4 tag libraries

-  Core (c)
-  Formatting (fmt)
-  SQL (sql)
-  XML (c)

[The Jakarta JSTL 1.2 Java Doc](https://jakarta.ee/specifications/tags/1.2/apidocs/)
