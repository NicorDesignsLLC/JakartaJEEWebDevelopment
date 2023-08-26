package com.nicordesigns;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "loginServlet",
        urlPatterns = "/login"
)
public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
	
	private static final Map<String, String> userDatabase = new Hashtable<>();

	//Simple in memory DB to store users and passwords
    static {
        userDatabase.put("Nicolaas", "password");
        userDatabase.put("Danette", "wordpass");
        userDatabase.put("Tom", "green");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
      //Check if the User is already logged in
        if(request.getParameter("logout") != null)
        {
        	//Send to login page if not
            session.invalidate();
            response.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("username") != null) 
        {
        	//Send to application if logged in
        	session.setAttribute("username", session.getAttribute("username").toString());
            response.sendRedirect("charityRegistrationServlet");
            return;
        }

        request.setAttribute("loginFailed", false);
        
        //Redirect to the login page
        request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
               .forward(request, response);
    }

    //The login.jsp for POSTs to this method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	
    	HttpSession session = request.getSession();
        
    	if(request.getParameter("logout") != null) // Add logout functionality
        {
            session.invalidate();
            response.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("username") != null)
        {
            response.sendRedirect("registrations");
            return;
        }
    	
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //Forward to application if the login is successful
        if(username == null || password == null ||
                !LoginServlet.userDatabase.containsKey(username) ||
                !password.equals(LoginServlet.userDatabase.get(username)))
        {
            
        	request.setAttribute("loginFailed", true); //Use this attribute to indicate login failure
            
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                   .forward(request, response);
        }
        else
        {
            request.changeSessionId();
            session.setAttribute("username", username);
            response.sendRedirect("registrations");
        }
    }
}
