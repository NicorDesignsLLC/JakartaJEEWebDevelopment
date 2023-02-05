package com.nicordesigns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "registrationServlet", urlPatterns = { "/registrations" }, loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 5_242_880, // 5MB
		maxFileSize = 20_971_520L, // 20MB
		maxRequestSize = 41_943_040L // 40MB
)
public class RegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4441314868375700166L;

	private volatile int REGISTRATION_ID_SEQUENCE = 1;

	private Map<Integer, Registration> registrationDatabase = new LinkedHashMap<>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Using Filters is the proper way to Authenticate Users
//		//Check that the Users are logged in
//    	if(request.getSession().getAttribute("username") == null)
//        {
//            response.sendRedirect("login");
//            return;
//        } 
    	    	
    	
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.showRegistrationForm(request, response);
			break;
		case "view":
			this.viewRegistration(request, response);
			break;
		case "download":
			this.downloadAttachment(request, response);
			break;
		case "list":
		default:
			this.listRegistrations(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Using Filters is the proper way to Authenticate Users
		
		//Check that the Users are logged in
//    	if(request.getSession().getAttribute("username") == null)
//        {
//            response.sendRedirect("login");
//            return;
//        } 
    	
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.createRegistration(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("registrations");
			break;
		}
	}

	private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/view/registrationForm.jsp").forward(request, response);
	}

	private void viewRegistration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("registrationId");
		Registration registration = this.getRegistration(idString, response);
		if (registration == null)
			return;

		request.setAttribute("registrationId", idString);
		request.setAttribute("registration", registration);

		request.getRequestDispatcher("/WEB-INF/jsp/view/viewRegistration.jsp").forward(request, response);

	}

	private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("registrationId");
		Registration registration = this.getRegistration(idString, response);
		if (registration == null)
			return;

		String name = request.getParameter("attachment");
		if (name == null) {
			response.sendRedirect("registrations?action=view&registrationId=" + idString);
			return;
		}

		Attachment attachment = registration.getAttachment(name);
		if (attachment == null) {
			response.sendRedirect("registrations?action=view&registrationId=" + idString);
			return;
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
		response.setContentType("application/octet-stream");

		ServletOutputStream stream = response.getOutputStream();
		stream.write(attachment.getContents());
	}

	private void listRegistrations(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("registrationDatabase", this.registrationDatabase);

		request.getRequestDispatcher("/WEB-INF/jsp/view/listRegistrations.jsp").forward(request, response);
	}

	private void createRegistration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Registration registration = new Registration();
		
		HttpSession session = request.getSession();
		registration.setUserName((String) session.getAttribute("username"));
		
		registration.setSubject(request.getParameter("subject"));
		registration.setBody(request.getParameter("body"));
		registration.setDateCreated(Instant.now());

		Part filePart = request.getPart("file");
		if (filePart != null && filePart.getSize() > 0) {
			Attachment attachment = this.processAttachment(filePart);
			if (attachment != null)
				registration.addAttachment(attachment);
		}

		int id;
		synchronized (this) {
			id = this.REGISTRATION_ID_SEQUENCE++;
			this.registrationDatabase.put(id, registration);
		}

		response.sendRedirect("registrations?action=view&registrationId=" + id);
	}

	private Attachment processAttachment(Part filePart) throws IOException {
		InputStream inputStream = filePart.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		int read;
		final byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

		Attachment attachment = new Attachment();
		attachment.setName(filePart.getSubmittedFileName());
		attachment.setContents(outputStream.toByteArray());

		return attachment;
	}

	private Registration getRegistration(String idString, HttpServletResponse response)
			throws ServletException, IOException {
		if (idString == null || idString.length() == 0) {
			response.sendRedirect("registrations");
			return null;
		}

		try {
			Registration registration = this.registrationDatabase.get(Integer.parseInt(idString));
			if (registration == null) {
				response.sendRedirect("registrations");
				return null;
			}
			return registration;
		} catch (Exception e) {
			response.sendRedirect("registrations");
			return null;
		}
	}

}
