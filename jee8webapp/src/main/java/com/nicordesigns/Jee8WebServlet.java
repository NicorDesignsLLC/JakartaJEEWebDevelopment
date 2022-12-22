/**
 * 
 */
package com.nicordesigns;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nicok
 *
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = { "/hello-world" }, loadOnStartup = 1)
public class Jee8WebServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_USER = "Guest";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("user");
		if (user == null)
			user = Jee8WebServlet.DEFAULT_USER;

		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append("    <head>\r\n")
				.append("        <title>Hello Associate Application</title>\r\n").append("    </head>\r\n")
				.append("    <body>\r\n").append("        Hello, ").append(user).append("!<br/><br/>\r\n")
				.append("        <form action=\"hello-world\" method=\"GET\">\r\n")
				.append("            Enter your name:<br/>\r\n")
				.append("            <input type=\"text\" name=\"user\"/><br/>\r\n")
				.append("            <input type=\"submit\" value=\"Submit\"/>\r\n").append("        </form>\r\n")
				.append("    </body>\r\n").append("</html>\r\n");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);

	}

	@Override
	public void init() throws ServletException {
		System.out.println("In HelloWorldServlet init()");
	}

	@Override
	public void destroy() {
		System.out.println("In HelloWorldServlet destroy()");
	
	}

	/**
	 * 
	 */
	public Jee8WebServlet() {
		// TODO Auto-generated constructor stub
	}

}
