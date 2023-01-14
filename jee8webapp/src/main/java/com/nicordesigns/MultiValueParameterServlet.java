package com.nicordesigns;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MultiValueParameterServlet extends HttpServlet
{
    private static final long serialVersionUID = -54154489563724779L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
        .append("<html>\r\n")
        .append("    <head>\r\n")
        .append("        <title>Hello World Application</title>\r\n")
        .append("    </head>\r\n")
        .append("    <body>\r\n")
        .append("        <form action=\"servletMultiValueParameters\" method=\"POST\">\r\n")
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String[] colors = request.getParameterValues("color");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Hello World Application</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append("        <h2>Your Selections</h2>\r\n");

        if(colors == null)
            writer.append("        You did not select any colors.\r\n");
        else
        {
            writer.append("        <ul>\r\n");
            for(String color : colors)
            {
                writer.append("        <li>").append(color).append("</li>\r\n");
            }
            writer.append("        </ul>\r\n");
        }

        writer.append("    </body>\r\n")
              .append("</html>\r\n");
    }
}
