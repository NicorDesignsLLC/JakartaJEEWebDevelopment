package com.nicordesigns.site.filters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "compressedServlet", urlPatterns = "/servlet")
public class CompressedServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4507395444613784610L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream()
                .println("This Servlet response may be compressed.");
    }
}
