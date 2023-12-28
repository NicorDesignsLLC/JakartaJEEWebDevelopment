package com.nicordesigns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "charityRegistrationServlet", urlPatterns = { "/charityRegistrationServlet" }, loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CharityRegistrationServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger();

    private static final long serialVersionUID = 1L;

    private volatile int CHARITY_ID_SEQUENCE = 1;

    private Map<Integer, Registration> charityRegistrationDatabase = new LinkedHashMap<>();

    public CharityRegistrationServlet() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("GET request received.");
        String resourcesMapping = "/resources/*";

        javax.servlet.ServletContext servletContext = getServletContext();
        javax.servlet.ServletRegistration defaultRegistration = servletContext.getServletRegistration("default");
        defaultRegistration.addMapping(resourcesMapping);

        String action = request.getParameter("action");
        if (action == null)
            action = "list";
        switch (action) {
        case "create":
            showRegistrationForm(request, response);
            break;
        case "view":
            viewRegistration(request, response);
            break;
        case "download":
            downloadFileAttachment(request, response);
            break;
        case "list":
        default:
            listRegistrations(request, response);
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("POST request received.");
        String action = request.getParameter("action");
        if (action == null)
            action = "list";
        switch (action) {
        case "create":
            createRegistration(request, response);
            break;
        case "list":
        default:
            response.sendRedirect("charityRegistrationServlet");
            break;
        }
    }

    private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/view/registrationFormTemplate.jsp").forward(request, response);

    }

    private void viewRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idString = request.getParameter("registrationId");
        log.traceEntry(idString);
        // TODO if registration is null see if it already exists in the DB
        // and if it does get it
        Registration registration = getRegistration(idString, response);

        if (registration == null)
            return;

        HttpSession session = request.getSession(false); // Get the existing session if it exists
        if (session != null) {
            String username = (String) session.getAttribute("username");

            request.setAttribute("registration.userName", username);
        }

        request.setAttribute("registrationId", idString);
        request.setAttribute("registration", registration);

        request.getRequestDispatcher("/WEB-INF/jsp/view/viewRegistrationTemplate.jsp").forward(request, response);
        log.traceExit();
    }

    private void downloadFileAttachment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idString = request.getParameter("registrationId");
        log.traceEntry(idString);
        Registration registration = getRegistration(idString, response);
        if (registration == null)
            return;

        String name = request.getParameter("attachment");
        if (name == null) {
            response.sendRedirect("charityRegistrationServlet?action=view&registrationId=" + idString);
            return;
        }

        FileAttachment attachment = registration.getAttachment(name);
        if (attachment == null) {
            log.info("Requested attachment {} not found on ticket {}.", name, idString);
            response.sendRedirect("charityRegistrationServlet?action=view&registrationId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(attachment.getContents());
        log.traceExit();
    }

    private void listRegistrations(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	log.debug("Listing Registrations.");
        HttpSession session = request.getSession(false); // Get the existing session if it exists
        if (session != null) {
            String username = (String) session.getAttribute("username");

            request.setAttribute("registration.userName", username);
        }

        request.setAttribute("charityRegistrationDatabase", this.charityRegistrationDatabase);

        request.getRequestDispatcher("/WEB-INF/jsp/view/listRegistrationsTemplate.jsp").forward(request, response);

    }

    private void createRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.traceEntry();
        Registration registration = new Registration();

        HttpSession session = request.getSession(false); // Get the existing session if it exists
        if (session != null) {
            String username = (String) session.getAttribute("username");
            registration.setUserName(username);
        }

        registration.setSubject(request.getParameter("charityInfo"));

        registration.setBody(request.getParameter("body"));
        registration.setDateCreated(Instant.now());

        Part filePart = request.getPart("file");
        if (filePart != null && filePart.getSize() > 0) {
            log.debug("Processing file attachment for Registration.");
            FileAttachment attachment = processFileAttachment(filePart);
            if (attachment != null)
                registration.addAttachment(attachment);
        }

        int id;
        synchronized (this) {
            id = CHARITY_ID_SEQUENCE++;
            this.charityRegistrationDatabase.put(id, registration);
        }

        response.sendRedirect("charityRegistrationServlet?action=view&registrationId=" + id);
        log.traceExit();
    }

    private FileAttachment processFileAttachment(Part filePart) throws IOException {
        log.traceEntry();
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        FileAttachment attachment = new FileAttachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        // return attachment;
        return log.traceExit(attachment);
    }

    private Registration getRegistration(String idString, HttpServletResponse response)
            throws ServletException, IOException {
        log.traceEntry(idString);
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("charityRegistrationServlet");
            return null;
        }

        try {
            Registration registration = this.charityRegistrationDatabase.get(Integer.parseInt(idString));
            if (registration == null) {
                response.sendRedirect("charityRegistrationServlet");
                log.traceExit();
                return null;
            }
            Objects.requireNonNull(registration.getAttachments());

            return registration;
        } catch (Exception e) {
            response.sendRedirect("charityRegistrationServlet");
            
            return null;
        }
    }
}
