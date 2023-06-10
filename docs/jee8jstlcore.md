# Using the Core Tag Library

The Core library contains most of the elements we will use te replace embedded Java code in our JSPs with logic such as iteration loops and conditional statements.

The basic core library taglib directive:

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


# [General Purpose Actions Core Tag Library](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#general-purpose-actions-core-tag-library)

		For Each of these have a code sample ready that matches the Charity Database Example

[<c:out> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:out)

[<c:url> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:url)

Add a param name value example here to demonstrate this and the context relative URL paths, also replace all href tags with this action in a JSP web app for constincy and security reasons.

[<c:if> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cif)

[<c:choose> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cchoose)

[<c:when> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cwhen)

[<c:otherwise> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cotherwise)

Add a couple of relative examples here to help explain the use of these conditional actions


[<c:foreEach> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#iterator-actions-core-tag-library) match the docs with logic here

[<c:forTokens> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:forTokens) 

same as forEach but used for delimited Strings and not a collection of Objects

[<c:redirect> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#credirect)

[<c:import> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cimport)

[<c:set> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:set)
[<c:remove> tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cremove)

#### Putting Core Library Tags to Use 
### Our Core Library Examples
Charity Address book - we start of with generating a project using the JEE 8 Maven Web Application Archetype and all the infrastructure and dependencies that we have set up in our charity-registration web application :

[https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-address-book](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-address-book)

Our index.jsp

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/index.jsp](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/index.jsp)


Our base.jspf

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/WEB-INF/jsp/base.jspf](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/WEB-INF/jsp/base.jspf)

We use the Charity Address POJO to list out the Address Book

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/java/com/nicordesigns/Address.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/java/com/nicordesigns/Address.java)


The ListServlet

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/java/com/nicordesigns/ListServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/java/com/nicordesigns/ListServlet.java)

creates an in memory database of Charity Addresses

and the list.jsp

use the Core Tag Library to list out the Address Book

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/WEB-INF/jsp/view/list.jsp](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/WEB-INF/jsp/view/list.jsp)

we will compile and run our app and have a look at

[http://localhost:8080/charity-address-book/list](http://localhost:8080/charity-address-book/list)

and

[http://localhost:8080/charity-address-book/list?empty](http://localhost:8080/charity-address-book/list?empty)


