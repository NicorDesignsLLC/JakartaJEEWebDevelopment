<%@ page session="false" import="com.nicordesigns.FileAttachment, com.nicordesigns.Registration" %>
<%
    String registrationId = (String)request.getAttribute("registrationId");
    Registration registration = (Registration)request.getAttribute("registration");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <style>
        .form-group {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Registration #<%= registrationId %>: <%= registration.getUserName() %></h2>
    <div class="row">
        <div class="col-xs-12">
            <p><i>Customer Name - <%= registration.getUserName() %></i></p>
            <p><%= registration.getBody() %></p>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <% if(registration.getNumberOfAttachments() > 0) { %>
                <p>Attachments:</p>
                <ul class="list-unstyled">
                    <% int i = 0;
                    for(FileAttachment a : registration.getAttachments()) {
                        if(i++ > 0) { %>
                            <li><a href="<c:url value="charityRegistrationServlet">
                                    <c:param name="action" value="download" />
                                    <c:param name="registrationId" value="<%= registrationId %>" />
                                    <!-- TODO Get the Attachment submit and display working again -->
                                    <c:param name="attachment" value="<%= a.getName() %>" />
                                </c:url>"><%= a.getName() %></a></li>
                        <% }
                    } %>
                </ul>
            <% } %>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <a href="<c:url value="/charityRegistrationServlet" />">Return to list Registrations</a>
        </div>
    </div>
</div>

<script src="../js/bootstrap.min.js"></script>
</body>
</html>

