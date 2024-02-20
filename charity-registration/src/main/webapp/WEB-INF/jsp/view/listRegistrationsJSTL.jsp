<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" import="java.util.Map,com.nicordesigns.site.FileAttachment,com.nicordesigns.site.Registration" %>
<c:set var="registrationDatabasejstl" value="${empty requestScope.charityRegistrationDatabase ? null : requestScope.charityRegistrationDatabase}" />
<html>
<head>
<title>Charity Registration</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"/>
    <!-- Custom CSS file for the color scheme -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom-style.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2 class="display-4 bg-primary custom-heading">Registrations</h2>
		<div class="form-group">
		<a href="<c:url value='charityRegistrationServlet'><c:param name='action' value='create' /></c:url>">Create Registration</a><br />
		<br />
		</div>
		
		<c:if test="${empty registrationDatabasejstl}">
			<i>jstl - There are no Registrations in the system.</i>
		</c:if>
		<c:if test="${not empty registrationDatabasejstl}">
		<i>jstl There are Registrations in the system.</i></br>
		    <!-- TODO Add all Fields in the Create Form here -->
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<td><div class="custom-text">Registration Number</div></td>
						<td><div class="custom-text">Charity Subject</div></td>
						<td><div class="custom-text">Charity Name</div></td>
						<td><div class="custom-text">Attachments:</div></td>
						
					</tr>
				</thead>
				<c:forEach var="id" items="${registrationDatabasejstl.keySet()}">
					<c:set var="idString" value="${id}" />
					<c:set var="registrationDB" value="${registrationDatabasejstl[id]}" />
					<c:set var="classSuccess" value="" />

					<tr class="${classSuccess}">
						<td> <div class="form-group">Number: <c:out value="${idString}" /> <a href="<c:url value='charityRegistrationServlet'><c:param name='action' value='view' /><c:param name='registrationId' value='${idString}' /></c:url>"></a></div></td>
						
						<td> <div class="form-group"><c:out value="${registrationDB.subject}" /></div></td>
						
						<td> <div class="form-group"><c:out value="${registrationDB.userName}" /></div></td>
						
						<td>  <div class="form-group">
							<c:if test="${registrationDB.numberOfAttachments > 0}">
								
								<c:forEach var="fileAttachment" items="${registrationDB.attachments}" varStatus="status">
									<c:set var="attachmentName" value="${fileAttachment.name}" />
									<a href="<c:url value='/charityRegistrationServlet'><c:param name='action' value='download' /><c:param name='registrationId' value='${idString}' /><c:param name='attachment' value='${attachmentName}' /></c:url>"><c:out value="${attachmentName}" /></a>
									<c:if test="${not status.last}">, </c:if>
								</c:forEach>
								<br /><br />
							</c:if>
							<br /></div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
	</div>
</body>
</html>
