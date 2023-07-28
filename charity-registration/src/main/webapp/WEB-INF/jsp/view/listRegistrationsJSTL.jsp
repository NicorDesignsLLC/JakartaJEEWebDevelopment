<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration" %>
<c:set var="registrationDatabasejstl" value="${empty requestScope.charityRegistrationDatabase ? null : requestScope.charityRegistrationDatabase}" />
<html>
<head>
<title>Charity Registration</title>
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
	<div class="container">
		<h2>Registrations</h2>
		<a href="<c:url value='charityRegistrationServlet'><c:param name='action' value='create' /></c:url>">Create Registration</a><br />
		<br />
		
		<c:if test="${empty registrationDatabasejstl}">
			<i>jstl - There are no Registrations in the system.</i>
		</c:if>
		<c:if test="${not empty registrationDatabasejstl}">
		<i>jstl There are Registrations in the system.</i></br>
		    <!-- TODO Add all Fields in the Create Form here -->
			<table class="table table-striped" border="1">
				<thead>
					<tr>
						<td>Registration Number</td>
						<td>Charity Subject</td>
						<td>Charity Name</td>
					</tr>
				</thead>
				<c:forEach var="id" items="${registrationDatabasejstl.keySet()}">
					<c:set var="idString" value="${id}" />
					<c:set var="registrationDB" value="${registrationDatabasejstl[id]}" />
					<c:set var="classSuccess" value="" />

					<tr class="${classSuccess}">
						<td>Number: <c:out value="${idString}" /> <a href="<c:url value='charityRegistrationServlet'><c:param name='action' value='view' /><c:param name='registrationId' value='${idString}' /></c:url>"></a></td>
						<td><c:out value="${registrationDB.subject}" /></td>
						<td><c:out value="${registrationDB.userName}" /></td>
						<td>
							<c:if test="${registrationDB.numberOfAttachments > 0}">
								Attachments:
								<c:forEach var="fileAttachment" items="${registrationDB.attachments}" varStatus="status">
									<c:set var="attachmentName" value="${fileAttachment.name}" />
									<a href="<c:url value='/charityRegistrationServlet'><c:param name='action' value='download' /><c:param name='registrationId' value='${idString}' /><c:param name='attachment' value='${attachmentName}' /></c:url>"><c:out value="${attachmentName}" /></a>
									<c:if test="${not status.last}">, </c:if>
								</c:forEach>
								<br /><br />
							</c:if>
							<br />
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
	</div>
</body>
</html>
