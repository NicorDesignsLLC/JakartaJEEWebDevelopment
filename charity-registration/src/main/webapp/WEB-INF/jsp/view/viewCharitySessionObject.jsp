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
					List</a><br />
				<br />
			</div>
			<div class="row">
				<a
					href="<c:url value="/charitySession?action=emptyCharitySessionObject" />">Empty
					Charity Session Object</a><br />
				<br />
			</div>
			<div class="row">
                <div class="custom-text">JSTL CODE START</div> 
				<div class="form-group">
					<c:choose>
						<c:when
							test="${categoryHolder == null || categoryHolder.size() == 0}">
							<div class="custom-text">Your category holder is empty.</div>
						</c:when>
						<c:otherwise>
							<!-- Your else section code goes here -->
							<div class="custom-text">Your category holder is not empty.</div>
						</c:otherwise>
					</c:choose>
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
