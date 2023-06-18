# Using the Core Tag Library

The Core library contains most of the elements we will use te replace embedded Java code in our JSPs with logic such as iteration loops and conditional statements.

The basic core library JSTL taglib directive :

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

##### [Jakarta Standard Tag Library Core Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8-jstl12-core-end)