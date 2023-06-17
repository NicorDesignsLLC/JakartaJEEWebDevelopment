<%--@elvariable id="registration" type="com.nicordesigns.Registration"--%>
<!-- Above comment is used to help out IDE's -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration, java.time.Instant, java.time.LocalDate, java.time.ZoneOffset, java.time.ZoneId , java.time.format.DateTimeFormatter, java.util.Date"%>

<c:set var="registrationId" value="${requestScope.registrationId}" />
<c:set var="registration" value="${requestScope.registration}" />
<c:set var="PATTERN_FORMAT" value="dd.MM.yyyy" />

<!DOCTYPE html>
<html>
<head>
<title>Charity Registration</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
<style>
.form-group {
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<h2>
			<b>Registration # ${registrationId} User Name:
				${registration.userName}</b>
		</h2>
		<div class="row">
			<div class="col-xs-12">
				<table class="table table-striped" border="1">
					<thead>

						<tr>
							<td><i>Charity User Name</i></td>
							<td><i>Charity Registration Body</i></td>
							<td><i>Charity Registration Subject</i></td>
							<td><i>Charity Registration Date</i></td>
						</tr>
						<tr>
							<td>${registration.userName}</td>
							<td>${registration.body}</td>
							<td>${registration.subject}</td>
							<td>
								<fmt:formatDate type="time" value="${registration.createdDate}" /><br>
								<fmt:formatDate value="${registration.createdDate}"
									pattern="${PATTERN_FORMAT}" /></td>
						</tr>

					</thead>
				</table>
			</div>
		</div>
		<div class="row">

			<div class="col-xs-12">
				<c:if test="${registration.numberOfAttachments > 0}">
		Attachments:
		<c:forEach var="fileAttachment" items="${registration.attachments}"
						varStatus="status">
						<c:set var="attachmentName" value="${fileAttachment.name}" />
						<c:url value="/charityRegistrationServlet" var="fileDownloadURL">
							<c:param name="action" value="download" />
							<c:param name="registrationId" value="${registrationId}" />
							<c:param name="attachment" value="${attachmentName}" />
						</c:url>
						<a href="<c:out value="${fileDownloadURL}" />">${attachmentName}</a>

						<c:if test="${not status.last}">, </c:if>
		</c:forEach>
					<br />
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<a href="<c:url value="/charityRegistrationServlet" />">Return
					to list Registrations</a>
			</div>
		</div>
	</div>

	<script src="../js/bootstrap.min.js"></script>
</body>
</html>

