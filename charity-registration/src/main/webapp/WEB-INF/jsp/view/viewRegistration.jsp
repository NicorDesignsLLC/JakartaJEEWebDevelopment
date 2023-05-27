<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration, java.time.Instant, java.time.ZoneId, java.time.format.DateTimeFormatter"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
			Registration #<%=registrationId%>:
			<%=registration.getUserName()%></h2>
		<div class="row">
			<div class="col-xs-12">
				<!-- TODO Add all Fields in the Create Form here -->
				<table class="table table-striped" border="1">
					<thead>
						<tr>
							<td><i>Charity User Name - </i></td>
							<td><%=registration.getUserName()%></td>
						</tr>
						<tr>
							<td><i>Charity Registration Body - </i></td>
							<td><p><%=registration.getBody()%></p></td>
						</tr>
						<tr>
							<td><i>Charity Registration Subject - </i></td>
							<td><p><%=registration.getSubject()%></p></td>
							<td>Charity Registration Date</td>
							<td><p><%=formattedInstantDate%></p></td>
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

