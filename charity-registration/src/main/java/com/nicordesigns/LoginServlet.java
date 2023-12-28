package com.nicordesigns;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(
        name = "loginServlet",
        urlPatterns = "/login"
)
public class LoginServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
	
    private static final Logger log = LogManager.getLogger();
	private static final HashMap<String, String> userDatabase = new HashMap<>();

	//Simple in memory DB to store users and passwords
    static {
        userDatabase.put("Nicolaas", "Black");
        userDatabase.put("Danette", "White");
        userDatabase.put("Tom", "Green");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
      //Check if the User is already logged in
        if(request.getParameter("logout") != null)
        {
        	if(log.isDebugEnabled())
                log.debug("User {} logged out.", session.getAttribute("username"));
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
    		log.debug("User about to be logged out.");
            session.invalidate();
            response.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("username") != null)
        {
            response.sendRedirect("charityRegistrationServlet");
            return;
        }
    	
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //Forward to application if the login is successful
        if(username == null || password == null ||
                !LoginServlet.userDatabase.containsKey(username) ||
                !password.equals(LoginServlet.userDatabase.get(username)))
        {
        	log.warn("Login failed for user {}.", username);
        	request.setAttribute("loginFailed", true); //Use this attribute to indicate login failure
            
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                   .forward(request, response);
        }
        else
        {
        	log.info("User {} successfully logged in.", username);
            request.changeSessionId();
            session.setAttribute("username", username);
            response.sendRedirect("charityRegistrationServlet");
        }
    }
}
