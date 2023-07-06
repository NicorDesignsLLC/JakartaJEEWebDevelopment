package com.nicordesigns;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(
        name = "charitySessionServlet",
        urlPatterns = "/charitySession"
)
public class CharitySessionServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
	
	private final Map<Integer, String> categories = new Hashtable<>();

    public CharitySessionServlet()
    {
        this.categories.put(1, "Animals");
        this.categories.put(2, "Arts, Culture, Humanities");
        this.categories.put(3, "Community Development");
        this.categories.put(4, "Education");
        this.categories.put(5, "Environment");
        this.categories.put(6, "Health");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	
    	
        String action = request.getParameter("action");
        if(action == null)
            action = "browse";

        switch(action)
        {
            case "addToCharitySession":
                this.addToCharitySession(request, response);
                break;

            case "emptyCharitySessionObject":
                this.emptyCharitySessionObject(request, response);
                break;

            case "viewCharitySession":
                this.viewCharitySession(request, response);
                break;

            case "browse":
            default:
                this.browse(request, response);
                break;
        }
    }

    private void addToCharitySession(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException
    {
        int categoryId;
        try
        {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        }
        catch(Exception e)
        {
            response.sendRedirect("charitySession");
            return;
        }

        HttpSession session = request.getSession();
        if(session.getAttribute("categoryHolder") == null)
            session.setAttribute("categoryHolder", new Hashtable<Integer, Integer>());

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> categoryHolder =
                (Map<Integer, Integer>)session.getAttribute("categoryHolder");
        if(!categoryHolder.containsKey(categoryId))
            categoryHolder.put(categoryId, 0);
        categoryHolder.put(categoryId, categoryHolder.get(categoryId) + 1);

        response.sendRedirect("charitySession?action=viewCharitySession");
    }

    private void emptyCharitySessionObject(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getSession().removeAttribute("categoryHolder");
        response.sendRedirect("charitySession?action=viewCharitySession");
    }

    private void viewCharitySession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setAttribute("categories", this.categories);
        request.getRequestDispatcher("/WEB-INF/jsp/view/viewCharitySessionObject.jsp")
                .forward(request, response);
    }

    private void browse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setAttribute("categories", this.categories);
        request.getRequestDispatcher("/WEB-INF/jsp/view/browse.jsp")
               .forward(request, response);
    }
}
