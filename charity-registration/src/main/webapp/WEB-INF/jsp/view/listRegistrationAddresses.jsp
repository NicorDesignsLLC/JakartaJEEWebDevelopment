<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- We include the tag libraries because Eclipse as yet can not pick up the base.jspf imports set up in the web.xml -->
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="title.browser" /></title>
<style>
/* Add custom spacing between form elements */
.form-group {
	margin-bottom: 20px;
}
</style>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<h2>
		<fmt:message key="title.page" />
	</h2>
	<c:choose>
		<c:when test="${fn:length(addresses) == 0}">
			<i><fmt:message key="message.noContacts" /></i>
		</c:when>
		<c:otherwise>
			<table class="table table-striped" border="1">
				<thead>
					<tr>
						<td>Charity Name</td>
						<td>Charity Web URL</td>
						<td>Charity Phone Number</td>
						<td><fmt:message key="label.registrationday" /></td>
						<td><fmt:message key="label.creationDate" /></td>
					</tr>
				</thead>
				<c:forEach items="${addresses}" var="charity">
					<c:set var="classSuccess" value="" />
					<tr class="${classSuccess}">
						<td><b> <c:out
									value="${charity.charityName}, ${charity.charityId}" />
						</b> <br /></td>
						<td><a href="<c:out value="${charity.webAddress}" />"> <c:out
									value="${charity.webAddress}" />
						</a> <br /></td>
						<td><c:out value="${charity.phoneNumber}" /> <br /></td>
						
						<c:if test="${charity.registrationday != null}">
							<td><fmt:formatDate pattern="dd-MM-yyyy"
									value="${charity.registrationday}" /> <br />
							</td>
						
						</c:if>
						<td><fmt:parseDate value="${charity.dateCreated}" type="date"
								pattern="yyyy-MM-dd" var="parsedDate" />

						<fmt:formatDate value="${parsedDate}" type="date"
								pattern="dd.MM.yyyy" var="stdDatum" /> ${stdDatum}<br /> <br />
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>
