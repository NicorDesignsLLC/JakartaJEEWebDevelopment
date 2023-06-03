<%--@elvariable id="registration" type="com.nicordesigns.Registration"--%>
<!-- Above comment is used to help out IDE's -->
<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration, java.time.Instant, java.time.LocalDate, java.time.ZoneOffset, java.time.ZoneId , java.time.format.DateTimeFormatter"%>
<%
String registrationId = (String) request.getAttribute("registrationId");
Registration registration = (Registration) request.getAttribute("registration");
final String PATTERN_FORMAT = "dd.MM.yyyy";
DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
String formattedInstantDate = formatter.format(registration.getDateCreated());
Instant instant = registration.getDateCreated();
LocalDate localDate = LocalDate.ofInstant(instant, ZoneOffset.UTC);
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
			Registration #<%=registrationId%> EL: ${registrationId}
			<%=registration.getUserName()%>
			EL: ${registration.userName}
			</h2>
		<div class="row">
			<div class="col-xs-12">
				<!-- TODO Add all Fields in the Create Form here -->
				<table class="table table-striped" border="1">
					<thead>
						<tr>
							<td><i>Charity User Name - </i></td>
							<td><%=registration.getUserName()%></td>
							<td>EL: ${registration.userName}</td>
							
						</tr>
						<tr>
							<td><i>Charity Registration Body - </i></td>
							<td><p><%=registration.getBody()%></p></td>
							<td>EL: ${registration.body}</td>
						</tr>
						<tr>
							<td><i>Charity Registration Subject - </i></td>
							<td><p><%=registration.getSubject()%></p></td>
							<td><p>EL ${registration.subject}</p></td>
							<td>Charity Registration Date</td>
							<td><p><%=formattedInstantDate%></p></td>
							<td><p>EL:<!--TODO Function   ${formattedInstantDate}-->
							<c:set var="now" value="<%= new java.util.Date()%>"/>
							</p>
							</td>
							<td>
							<fmt:formatDate type="time" value="${now}">${now}</fmt:formatDate>
							</td>
							<td>
							<c:set var="localDate" value="<%= localDate%>"/>
							<fmt:formatDate type="date" value="${localDate}" var="formattedDate" pattern="MM-dd-yyyy" >${localDate}</fmt:formatDate>
							</p>
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

