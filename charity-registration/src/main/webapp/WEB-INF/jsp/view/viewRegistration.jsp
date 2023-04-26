<%@ page session="false" import="com.nicordesigns.FileAttachment, com.nicordesigns.Registration" %>
<%
    String registrationId = (String)request.getAttribute("registrationId");
    Registration registration = (Registration)request.getAttribute("registration");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Registration #<%= registrationId %>: <%= registration.getUserName() %></h2>
        <i>Customer Name - <%= registration.getUserName() %></i><br /><br />
        <%= registration.getBody() %><br /><br />
        <%
            if(registration.getNumberOfAttachments() > 0)
            {
                %>Attachments: <%
                int i = 0;
                for(FileAttachment a : registration.getAttachments())
                {
                    if(i++ > 0)
                        out.print(", ");
                    %><a href="<c:url value="/registrations">
                        <c:param name="action" value="download" />
                        <c:param name="registrationId" value="<%= registrationId %>" />
                        <c:param name="attachment" value="<%= a.getName() %>" />
                    </c:url>"><%= a.getName() %></a><%
                }
                %><br /><br /><%
            }
        %>
        <a href="<c:url value="/registrations" />">Return to list Registrations</a>
    </body>
</html>
