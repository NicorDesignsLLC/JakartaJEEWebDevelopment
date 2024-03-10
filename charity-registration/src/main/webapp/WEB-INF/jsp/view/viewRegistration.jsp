<%--@elvariable id="registration" type="com.nicordesigns.Registration"--%>
<!-- Above comment is used to help out IDE's -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"
	import="java.util.Map,com.nicordesigns.FileAttachment,com.nicordesigns.Registration, java.time.Instant, java.time.LocalDate, java.time.ZoneOffset, java.time.ZoneId , java.time.format.DateTimeFormatter, java.util.Date"%>


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
    <title>Registration Form</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"/>
    <!-- Custom CSS file for the color scheme -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom-style.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
	
	     <h2 class="display-4 bg-primary custom-heading">Registration #<%=registrationId%>:
			<%=registration.getUserName()%></h2>  
		<div class="row">
			<div class="col-xs-12">
				<!-- TODO Add all Fields in the Create Form here -->
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
						    <td><div class="form-group">Charity User Name - </div></td>
						    <td><div class="form-group"><%=registration.getUserName()%></div></td>
						</tr>
						<tr>
				   		    <td><div class="form-group">Charity Registration Body - </div></td>
						    <td><div class="form-group"><%=registration.getBody()%></div></td>
						</tr>
						<tr>
				   		    <td><div class="form-group">Charity Registration Body - </div></td>
						    <td><div class="form-group"><%=registration.getSubject()%></div></td>
				
				   		    <td><div class="form-group">Charity Registration Date</div></td>
						    <td><div class="form-group"><%=formattedInstantDate%></div></td>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
			    <div class="form-group">
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
