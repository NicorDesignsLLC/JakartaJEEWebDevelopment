<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Map"%>
<c:set var="categories" value="${requestScope.categories}" />
<c:set var="categoryHolder" value="${requestScope.categoryHolder}" />
<!DOCTYPE html>
<html>
<head>
<title>View Charity Session Object</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" />
<!-- Custom CSS file for the color scheme -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/custom-style.css" />
<script
	src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2 class="display-4 bg-primary custom-heading">View Charity
			Session Object</h2>
		<div class="form-group">
			<div class="row">
				<a href="<c:url value="/charitySession" />">Charity Category
					List</a><br /> <br />
			</div>
			<div class="row">
				<a
					href="<c:url value="/charitySession?action=emptyCharitySessionObject" />">Empty
					Charity Session Object</a><br /> <br />
			</div>
			<div class="row">
				<div class="custom-text">JSTL CODE START</div>
				<div class="form-group">
					<!-- Check if categoryHolder is null or empty -->
					<c:if test="${empty categoryHolder}">
					    Your category holder is empty.
					</c:if>
					
					<!-- Otherwise, loop through categoryHolder and display categories and quantities -->
					<c:forEach items="${categoryHolder}" var="entry">
					    <c:set var="id" value="${entry.key}" />
					    <c:set var="quantity" value="${entry.value}" />
					    ${categories[id]} (quantity: ${quantity})<br />
					</c:forEach>
				</div>
				<div class="custom-text">JSTL CODE END</div>

			</div>
			<div class="row">
				<%
				@SuppressWarnings("unchecked")
				Map<Integer, String> categories = (Map<Integer, String>) request.getAttribute("categories");

				@SuppressWarnings("unchecked")
				Map<Integer, Integer> categoryHolder = (Map<Integer, Integer>) session.getAttribute("categoryHolder");

				out.println("Embedded Java");
				if (categoryHolder == null || categoryHolder.size() == 0)
					out.println("Your category holder is empty.");
				else {
					for (int id : categoryHolder.keySet()) {
						out.println(categories.get(id) + " (qty: " + categoryHolder.get(id) + ")<br />");
					}
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>
