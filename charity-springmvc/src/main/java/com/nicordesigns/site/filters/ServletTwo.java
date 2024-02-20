package com.nicordesigns.site.filters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servletTwo", urlPatterns = "/servletTwo")
public class ServletTwo extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5347292363801562113L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("Entering ServletTwo.doGet().");
        response.getWriter().write("Servlet Two");
        System.out.println("Leaving ServletTwo.doGet().");
    }
}
