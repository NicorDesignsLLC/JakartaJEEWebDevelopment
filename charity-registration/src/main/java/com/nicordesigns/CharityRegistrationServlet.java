package com.nicordesigns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "CharityRegistrationServlet", urlPatterns = { "/charityRegistrationServlet" }, loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CharityRegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private volatile int CHARITY_ID_SEQUENCE = 1;

	private Map<Integer, Registration> charityRegistrationDatabase = new LinkedHashMap<>();

	public CharityRegistrationServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.showRegistrationForm(response);
			break;
		case "view":
			this.viewRegistration(request, response);
			break;
		case "download":
			this.downloadFileAttachment(request, response);
			break;
		case "list":
		default:
			this.listRegistrations(response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.createRegistration(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("charityRegistrationServlet");
			break;
		}
	}

	private void showRegistrationForm(HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = this.writeHeader(response);

		writer.append("<h2>Register a new Charity</h2>\r\n");
		writer.append("<form method=\"POST\" action=\"charityRegistrationServlet\" ").append("enctype=\"multipart/form-data\">\r\n");
		writer.append("<input type=\"hidden\" name=\"action\" ").append("value=\"create\"/>\r\n");
		writer.append("Charity Administrator Name<br/>\r\n");
		writer.append("<input type=\"text\" name=\"userName\"/><br/><br/>\r\n");
		writer.append("Charity Name<br/>\r\n");
		writer.append("<input type=\"text\" name=\"charityName\"/><br/><br/>\r\n");
		writer.append("Charity Information<br/>\r\n");
		writer.append("<textarea name=\"charityInfo\" rows=\"5\" cols=\"30\">").append("</textarea><br/><br/>\r\n");
		writer.append("<b>Tax Documentation File</b><br/>\r\n");
		writer.append("<input type=\"file\" name=\"file1\"/><br/><br/>\r\n");
		writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
		writer.append("</form>\r\n");

		this.writeFooter(writer);
	}

	private void viewRegistration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("registrationId");
		Registration registration = this.getRegistration(idString, response);
		if (registration == null)
			return;

		PrintWriter writer = this.writeHeader(response);

		writer.append("<h2>Registration #").append(idString).append(": ").append(registration.getSubject())
				.append("</h2>\r\n");
		writer.append("<i>Customer Name - ").append(registration.getUserName()).append("</i><br/><br/>\r\n");
		writer.append(registration.getBody()).append("<br/><br/>\r\n");

		if (registration.getNumberOfAttachments() > 0) {
			writer.append("FileAttachments: ");
			int i = 0;
			for (FileAttachment attachment : registration.getAttachments()) {
				if (i++ > 0)
					writer.append(", ");
				writer.append("<a href=\"charityRegistrationServlet?action=download&registrationId=").append(idString)
						.append("&attachment=").append(attachment.getName()).append("\">").append(attachment.getName())
						.append("</a>");
			}
			writer.append("<br/><br/>\r\n");
		}

		writer.append("<a href=\"charityRegistrationServlet\">Return to list registrations</a>\r\n");

		this.writeFooter(writer);
	}

	private void downloadFileAttachment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("registrationId");
		Registration registration = this.getRegistration(idString, response);
		if (registration == null)
			return;

		String name = request.getParameter("attachment");
		if (name == null) {
			response.sendRedirect("charityRegistrationServlet?action=view&registrationId=" + idString);
			return;
		}

		FileAttachment attachment = registration.getAttachment(name);
		if (attachment == null) {
			response.sendRedirect("charityRegistrationServlet?action=view&registrationId=" + idString);
			return;
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
		response.setContentType("application/octet-stream");

		ServletOutputStream stream = response.getOutputStream();
		stream.write(attachment.getContents());
	}

	private void listRegistrations(HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = this.writeHeader(response);

		writer.append("<h2>Registrations</h2>\r\n");
		writer.append("<a href=\"charityRegistrationServlet?action=create\">Register a Charity").append("</a><br/><br/>\r\n");

		if (this.charityRegistrationDatabase.size() == 0)
			writer.append("<i>There are no registrations in the system.</i>\r\n");
		else {
			for (int id : this.charityRegistrationDatabase.keySet()) {
				String idString = Integer.toString(id);
				Registration registration = this.charityRegistrationDatabase.get(id);
				writer.append("Registration #").append(idString)
						.append(": <a href=\"charityRegistrationServlet?action=view&registrationId=").append(idString).append("\">")
						.append(registration.getSubject()).append("</a> (user: ").append(registration.getUserName())
						.append(")<br/>\r\n");
			}
		}

		this.writeFooter(writer);
	}

	private void createRegistration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Registration registration = new Registration();
		registration.setUserName(request.getParameter("userName"));
		registration.setSubject(request.getParameter("charityName"));
		registration.setBody(request.getParameter("charityInfo"));

		Part filePart = request.getPart("file1");
		if (filePart != null && filePart.getSize() > 0) {
			FileAttachment attachment = this.processFileAttachment(filePart);
			if (attachment != null)
				registration.addAttachment(attachment);
		}

		int id;
		synchronized (this) {
			id = this.CHARITY_ID_SEQUENCE++;
			this.charityRegistrationDatabase.put(id, registration);
		}

		response.sendRedirect("charityRegistrationServlet?action=view&registrationId=" + id);
	}

	private FileAttachment processFileAttachment(Part filePart) throws IOException {
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

		return attachment;
	}

	private Registration getRegistration(String idString, HttpServletResponse response)
			throws ServletException, IOException {
		if (idString == null || idString.length() == 0) {
			response.sendRedirect("charityRegistrationServlet");
			return null;
		}

		try {
			Registration registration = this.charityRegistrationDatabase.get(Integer.parseInt(idString));
			if (registration == null) {
				response.sendRedirect("charityRegistrationServlet");
				return null;
			}
			return registration;
		} catch (Exception e) {
			response.sendRedirect("charityRegistrationServlet");
			return null;
		}
	}

	private PrintWriter writeHeader(HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append("    <head>\r\n")
				.append("        <title>Charity Registration</title>\r\n").append("    </head>\r\n")
				.append("    <charityInfo>\r\n");

		return writer;
	}

	private void writeFooter(PrintWriter writer) {
		writer.append("    </charityInfo>\r\n").append("</html>\r\n");
	}

}