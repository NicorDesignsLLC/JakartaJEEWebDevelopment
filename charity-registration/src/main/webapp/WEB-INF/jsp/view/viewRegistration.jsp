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
    <title>Registration Form</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"/>
    <!-- Custom CSS file for the color scheme -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom-style.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
	    <h2 class="display-4 bg-primary custom-heading">Registration # ${registrationId}</h2>
		<h2 class="display-4 bg-primary custom-heading">
			User Name:
				${registration.userName}
		</h2>
		<div class="row">
			<div class="col-xs-12">
				<table class="table table-striped table-bordered">
					<thead>

						<tr>
							<td ><div class="custom-text">Charity User Name</div></td>
							<td><div class="custom-text">Charity Registration Body</div></td>
							<td><div class="custom-text">Charity Registration Subject</div></td>
							<td><div class="custom-text">Charity Registration Date</div></td>
						</tr>
						<tr>
							<td><div class="form-group">${registration.userName}</div></td>
							<td><div class="form-group">${registration.body}</div></td>
							<td><div class="form-group">${registration.subject}</div></td>
							<td><div class="form-group">
								<fmt:formatDate type="time" value="${registration.createdDate}" /><br>
								<fmt:formatDate value="${registration.createdDate}"
									pattern="${PATTERN_FORMAT}" />
									</div>
									</td>
						</tr>

					</thead>
				</table>
			</div>
		</div>
		<div class="row">

			<div class="form-group">
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
			<div class="form-group">
				<a href="<c:url value="/charityRegistrationServlet" />">Return
					to list Registrations</a>
			</div>
		</div>
	</div>
</body>
</html>

