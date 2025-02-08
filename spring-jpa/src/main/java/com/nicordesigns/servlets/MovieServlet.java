package com.nicordesigns.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nicordesigns.entities.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "MovieServlet", urlPatterns = {"/movies"})
public class MovieServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EntityManagerFactory emf;

    @Override
    public void init() {
        // Manually create EntityManagerFactory using persistence.xml
        emf = Persistence.createEntityManagerFactory("MovieDBPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Fetch all movies
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();

            // Configure ObjectMapper with JavaTimeModule
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            // Convert movie list to JSON using Jackson
            String jsonResponse = objectMapper.writeValueAsString(movies);

            // Write JSON response
            PrintWriter out = response.getWriter();
            out.write(jsonResponse);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}
