package com.nicordesigns;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "sessionListServlet",
        urlPatterns = "/sessions",
        loadOnStartup = 1
)
public class SessionListServlet extends HttpServlet
{
 	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
		//Using Filters is the proper way to Authenticate Users
//        if(request.getSession().getAttribute("username") == null)
//        {
//            response.sendRedirect("login");
//            return;
//        }

        request.setAttribute("numberOfSessions",
        		InMemorySessionDB.getNumberOfSessions());
        request.setAttribute("sessionList", InMemorySessionDB.getAllSessions());
        request.getRequestDispatcher("/WEB-INF/jsp/view/sessionsTemplate.jsp")
               .forward(request, response);
    }
}
