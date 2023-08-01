<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Map" %>
<c:set var="categories" value="${requestScope.categories}" />
<!DOCTYPE html>
<html>
<head>
	<title>Charity Category List</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"/>
    <!-- Custom CSS file for the color scheme -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom-style.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
</head>    
    <body>
        <h2 class="display-4 bg-primary custom-heading">Charity Category List</h2>
        <div class="form-group">
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
        </div>
    </body>
</html>
