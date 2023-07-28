<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false"
	import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration"%>
<%
@SuppressWarnings("unchecked")
Map<Integer, Registration> registrationDatabase = (Map<Integer, Registration>) request
		.getAttribute("charityRegistrationDatabase");
%>
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
				request.setAttribute("idString", idString);
				Registration registrationDB = registrationDatabase.get(id);
				request.setAttribute("registrationDB", registrationDB);
			%>
			<c:set var="regRow" value="registration" />
			<tr class="${classSucess}">
				<td>Number: <%=idString%>: <a
					href="<c:url value="charityRegistrationServlet">
                        <c:param name="action" value="view" />
                        <c:param name="registrationId" value="${idString}" />
                    </c:url>"></td>
				<td>${registrationDB.subject}<td>
				<td>${registrationDB.userName}</td>
				<td>
				<%
				if (registrationDB.getNumberOfAttachments() > 0) {
				%>Attachments:
				<%
				int i = 0;
				for (FileAttachment fileAttachment : registrationDB.getAttachments()) {
					if (i++ > 0)
						out.print(", ");
					request.setAttribute("fileAttachment", fileAttachment);
				%><a
					href="<c:url value="/charityRegistrationServlet">
                        <c:param name="action" value="download" />
                        <c:param name="registrationId" value="${idString}" />
                        <c:param name="attachment" value="${fileAttachment.name}" />
                    </c:url>">${fileAttachment.name}</a> 
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
