package com.nicordesigns.filters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servletThree", urlPatterns = "/servletThree")
public class ServletThree extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8458835960452216925L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("Entering ServletThree.doGet().");
        response.getWriter().write("Servlet Three");
        System.out.println("Leaving ServletThree.doGet().");
    }
}
