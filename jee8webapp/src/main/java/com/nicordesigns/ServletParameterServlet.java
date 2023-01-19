package com.nicordesigns;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletParameterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ServletParameterServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletConfig c = this.getServletConfig();
		PrintWriter writer = response.getWriter();

		writer.append("database: ").append(c.getInitParameter("database")).append(", server: ")
				.append(c.getInitParameter("server"));
	}

}
