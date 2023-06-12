<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration"%>
<%
@SuppressWarnings("unchecked")
Map<Integer, Registration> registrationDatabase = (Map<Integer, Registration>) request
		.getAttribute("charityRegistrationDatabase");
Registration registrationEL = (Registration) request.getAttribute("registration");
%>
<html>
<head>
<title>Customer Support</title>
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
		<a href="<c:url value="charityRegistrationServlet"><c:param name="action" value="create" /></c:url>">Create
			Registration</a><br />
		<br />
		<%
		if (registrationDatabase.size() == 0) {
		%><i>There are no Registrations in the system.</i>
		<%
		} else {
		%>
		<!-- TODO Add all Fields in the Create Form here -->
		<table class="table table-striped" border="1">
			<thead>
				<tr>
					<td>Registration Number</td>
					<td>Charity Subject</td>
					<td>Charity Name</td>
				</tr>
			</thead>
			<%
			for (int id : registrationDatabase.keySet()) {
				String idString = Integer.toString(id);
				Registration registration = registrationDatabase.get(id);
			%>
			<c:set var="classSucess" value="info" />
			<tr class="${classSucess}">
				<td>Number: <%=idString%>: <a
					href="<c:url value="charityRegistrationServlet">
                        <c:param name="action" value="view" />
                        <c:param name="registrationId" value="<%=idString%>" />
                    </c:url>"></td>
				<td>${registrationEL.subject}</td>
				<td>customer: ${registrationEL.userName}</td>
				<td>
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
                        <c:param name="registrationId" value="<%=idString%>" />
                        <c:param name="attachment" value="<%=fileAttachment.getName()%>" />
                    </c:url>"><%=fileAttachment.getName()%></a>EL: ${fileAttachment.name}
				<%
				}
				%><br />
				<br />
				<%
				}
				%>
				
				
				
				<br />
				</td>

			</tr>
			<%
			}
			}
			%>
		
</body>
</html>
