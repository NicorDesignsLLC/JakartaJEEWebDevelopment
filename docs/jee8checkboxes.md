## Using multipart parameter input forms in the JEE 8 Web Hello World Servlet module

##### [JEE 8 Web Forms Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-debug-start)

### 1. Now add the MultiValueParameterServlet - this time use the old web.xml to register the Servlet


	response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
        .append("<html>\r\n")
        .append("    <head>\r\n")
        .append("        <title>Hello World Application</title>\r\n")
        .append("    </head>\r\n")
        .append("    <body>\r\n")
        .append("        <form action=\"checkboxes\" method=\"POST\">\r\n")
        .append("Select the colors you like:<br/>\r\n")
        .append("<input type=\"checkbox\" name=\"color\" value=\"Yellow\"/>")
        .append(" Yellow<br/>\r\n")
        .append("<input type=\"checkbox\" name=\"color\" value=\"Black\"/>")
        .append(" Black<br/>\r\n")
        .append("<input type=\"checkbox\" name=\"color\" value=\"Orange\"/>")
        .append(" Orange<br/>\r\n")
        .append("<input type=\"checkbox\" name=\"color\" value=\"Red\"/>")
        .append(" Red<br/>\r\n")
        .append("<input type=\"checkbox\" name=\"color\" value=\"Blue\"/>")
        .append(" Blue<br/>\r\n")
        .append("<input type=\"submit\" value=\"Submit\"/>\r\n")
        .append("        </form>")
        .append("    </body>\r\n")
        .append("</html>\r\n");

##### Run the Web App

##### Demonstrate that both the doGet() and doPost() method will accept and display the name parameter by using URL and Form


##### [JEE 8 Hello World Servlet Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-servlet-finish)








