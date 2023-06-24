# Using the Core Tag Library

The Core Tag Library in Jakarta Standard Tag Library (JSTL) provides elements that can replace embedded Java code in JSPs with logic such as iteration loops and conditional statements.

To start using the Core library, include the following taglib directive at the top of your JSP:

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

You can refer to the [General Purpose Actions Core Tag Library](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#general-purpose-actions-core-tag-library) for detailed information about the available tags. Here are some key tags and their corresponding links:

- [`<c:out>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:out): Used for outputting data.
- [`<c:url>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:url): Helps create URL paths and handle query parameters.
- [`<c:if>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cif): Enables conditional execution of code blocks.
- [`<c:choose>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cchoose): Provides multiple conditional branches to choose from.
- [`<c:when>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cwhen): Used within `<c:choose>` to specify a conditional branch.
- [`<c:otherwise>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cotherwise): Used within `<c:choose>` as the default branch.
- [`<c:forEach>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#iterator-actions-core-tag-library): Performs iteration over a collection.
- [`<c:forTokens>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:forTokens): Similar to `<c:forEach>`, but used for delimited strings.
- [`<c:redirect>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#credirect): Redirects the user to a different URL.
- [`<c:import>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cimport): Imports the content of another resource into the current JSP.
- [`<c:set>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#c:set): Sets the value of a variable.
- [`<c:remove>` tag](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#cremove): Removes a scoped variable.

For further details on these tags and their usage, you can explore the provided links. Additionally, you can check out the [Jakarta Standard Tag Library Core Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8-jstl12-core-end) for practical examples of utilizing the Core Library tags.