# Introducing JSP Tags and JSTL

To conform to the Model-View-Controller (MVC) pattern, it is essential to remove Java code from JSP files. While Expression Language is a good starting point, it still relies on embedded Java code within JSPs. In this document, we will explore JSTL and JSP tags, building upon the previously encountered <c:url> and <c:redirect> tags.

Client-side development has evolved into a specialized domain involving HTML, CSS, and JavaScript frameworks.

### 1. Working with Tags

- [Jakarta 1.2 Tags Specification](https://jakarta.ee/specifications/tags/1.2/)
- [Jakarta 2.0 Specification Document](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html)
- [Oracle Taglibs Documentation](https://docs.oracle.com/cd/B10500_01/java.920/a96657/taglibs.htm)
- [Oracle JSTL Technologies](https://www.oracle.com/java/technologies/jstl.html)
- [Tomcat Standard Taglibs Documentation](https://tomcat.apache.org/taglibs/standard/)

JSP tags are specified as actions in the official specification, each performing a specific action. Learn more about how actions are documented [here](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#how-actions-are-documented).

To indicate the usage of JSTL tag libraries, we use the taglib directive at the top of our JSP file:

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

Note that the provided URL is not an actual working URL of the TLD but rather a naming convention used to locate the correct TLD by the browser and your IDE. The prefix follows a convention rather than a configuration.

The location of TLD files can be a source of confusion and frustration as it may vary depending on the JEE-compliant web server being used. The order in which the JSP parser locates TLD files is as follows:

1. The files are located based on the JEE 8 Specification within the JEE 8-compliant web container.
2. The <taglib> declarations within the <jsp-config> section of web.xml can indicate the TLD location in your web project.
3. The TLD jar files can be placed in the /META-INF/WEB-INF/lib directory, where the JSP parser will pick them up.
4. In the case of Tomcat 9.5, installing JSTL is required, and you need to configure your Eclipse Project Properties accordingly when deploying to your local Tomcat Server for debugging and running. Learn more about the confusion surrounding JSTL and Tomcat 9.5 [here](https://stackoverflow.com/questions/4928271/how-to-install-jstl-the-absolute-uri-http-java-sun-com-jstl-core-cannot-be-r).

To learn more about taglib directives, refer to the [Oracle JEE 5 Taglib Directives Tutorial](https://docs.oracle.com/javaee/5/tutorial/doc/bnamu.html).

We will be exploring the following four tag libraries:

- Core (c)
- Formatting (fmt)
- SQL (sql)
- XML (c)

For detailed documentation on Jakarta JSTL 1.2, refer to the [JavaDoc](https://jakarta.ee/specifications/tags/1.2/apidocs/).