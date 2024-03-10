<%@ page session="false"
	import="java.util.Map,com.nicordesigns.FileAttachment,com.nicordesigns.Registration"%>
<%
@SuppressWarnings("unchecked")
Map<Integer, Registration> registrationDatabase = (Map<Integer, Registration>) request
		.getAttribute("charityRegistrationDatabase");
%>
<html>
<head>
<title>Charity Registration</title>
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
		<h2 class="display-4 bg-primary custom-heading">Registrations</h2>
		<div class="form-group">
			<a
				href="<c:url value="charityRegistrationServlet">
            <c:param name="action" value="create" />
        </c:url>">Create
				Registration</a><br /> <br />
			<%
			if (registrationDatabase.size() == 0) {
			%><i>There are no Registrations in the system.</i>
			<%
			} else {
			%>
			<!-- TODO Add all Fields in the Create Form here -->
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<td><div class="custom-text">Registration Number</div></td>
						<td><div class="custom-text">Charity Subject</div></td>
						<td><div class="custom-text">Charity Name</div></td>
					</tr>
				</thead>
				<%
				for (int id : registrationDatabase.keySet()) {
					String idString = Integer.toString(id);
					Registration registration = registrationDatabase.get(id);
				%>
				<c:set var="classSucess" value="info" />
				<tr class="${classSucess}">
					<td>
						<div class="form-group">
							Registration #<%=idString%>: <a
								href="<c:url value="charityRegistrationServlet">
                        <c:param name="action" value="view" />
                        <c:param name="registrationId" value="<%=idString%>" />
                    </c:url>">
							</a>
						</div>
					</td>
					<td><%=registration.getSubject()%></a></td>
					<td>(customer: <%=registration.getUserName()%>)<br />
					</td>
					<td>(File Atttachment: <%=registration.getAttachments()%>)<br />
					</td>

				</tr>
				<%
				}
				}
				%>


			</table>
		</div>
	</div>

</body>
</html>