<%--@elvariable id="registration" type="com.nicordesigns.Registration"--%>
<!-- Above comment is used to help out IDE's -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration, java.time.Instant, java.time.LocalDate, java.time.ZoneOffset, java.time.ZoneId , java.time.format.DateTimeFormatter, java.util.Date"%>
<%
String registrationId = (String) request.getAttribute("registrationId");
Registration registration = (Registration) request.getAttribute("registration");
final String PATTERN_FORMAT = "dd.MM.yyyy";
DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
String formattedInstantDate = formatter.format(registration.getDateCreated());
%>
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
			<b>Registration # ${registrationId}
			User Name: ${registration.userName}</b>
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
							<td><%=formattedInstantDate%><br>
							${registration.dateCreated}<br>
							${registration.createdDate}
							<fmt:formatDate type="time" value="${registration.createdDate}"/><br>
							<fmt:formatDate value="${registration.createdDate}" pattern="dd.MM.yyyy"/>
							
							
							</td>
						</tr>
						
					</thead>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<%
				if (registration.getNumberOfAttachments() > 0) {
				%>Attachments:
				<%
				int i = 0;
				for (FileAttachment fileAttachment : registration.getAttachments()) {
					if (i++ > 0)
						out.print(", ");
				%><a
					href="<c:url value="/charityRegistrationServlet">
                        <c:param name="action" value="download" />
                        <c:param name="registrationId" value="<%=registrationId%>" />
                        <c:param name="attachment" value="<%=fileAttachment.getName()%>" />
                    </c:url>"><%=fileAttachment.getName()%></a>
				<%
				}
				%><br />
				<br />
				<%
				}
				%>
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

