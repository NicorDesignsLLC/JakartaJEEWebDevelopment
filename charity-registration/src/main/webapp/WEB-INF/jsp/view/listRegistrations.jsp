<%@ page session="false" import="java.util.Map, com.nicordesigns.FileAttachment, com.nicordesigns.Registration" %>
<%
    @SuppressWarnings("unchecked")
    Map<Integer, Registration> registrationDatabase =
            (Map<Integer, Registration>)request.getAttribute("charityRegistrationDatabase");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Registrations</h2>
        <a href="<c:url value="charityRegistrationServlet">
            <c:param name="action" value="create" />
        </c:url>">Create Registration</a><br /><br />
        <%
            if(registrationDatabase.size() == 0)
            {
                %><i>There are no Registrations in the system.</i><%
            }
            else
            {
                for(int id : registrationDatabase.keySet())
                {
                    String idString = Integer.toString(id);
                    Registration registration = registrationDatabase.get(id);
                    %>Registration #<%= idString %>: <a href="<c:url value="charityRegistrationServlet">
                        <c:param name="action" value="view" />
                        <c:param name="registrationId" value="<%= idString %>" />
                    </c:url>"><%= registration.getSubject() %></a> (customer:
        			<%= registration.getUserName() %>)<br /><%
                }
            }
        %>
    </body>
</html>
