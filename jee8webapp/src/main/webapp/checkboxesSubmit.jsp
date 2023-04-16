<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String[] colors = request.getParameterValues("color");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello World Application</title>
    </head>
    <body>
        <h2>Your Selections</h2>
        <%
            if(colors == null)
            {
        %>You did not select any colors.<%
            }
            else
            {
        %><ul><%
                for(String color : colors)
                {
                    out.println("<li>" + color + "</li>");
                }
        %></ul><%
            }
        %>
    </body>
</html>
