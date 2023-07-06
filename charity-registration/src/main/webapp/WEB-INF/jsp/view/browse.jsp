<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Charity Category List</title>
    </head>
    <body>
        <h2>Charity Category List</h2>
        <a href="<c:url value="/charitySession?action=viewCharitySession" />">View Charity Session Data Object</a><br /><br />
        <%
            @SuppressWarnings("unchecked")
            Map<Integer, String> categories =
                    (Map<Integer, String>)request.getAttribute("categories");

            for(int id : categories.keySet())
            {
                %><a href="<c:url value="/charitySession">
                    <c:param name="action" value="addToCharitySession" />
                    <c:param name="categoryId" value="<%= Integer.toString(id) %>"/>
                </c:url>"><%= categories.get(id) %></a><br /><%
            }
        %>
    </body>
</html>
