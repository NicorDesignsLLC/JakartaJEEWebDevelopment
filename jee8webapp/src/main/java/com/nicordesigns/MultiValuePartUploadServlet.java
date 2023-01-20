package com.nicordesigns;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "MultiValuePartUploadServlet", urlPatterns = { "/multiValuePartUploadServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class MultiValuePartUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -54154489563724779L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append("<head>\r\n")
				.append("<title>Upload Servlet</title>\r\n").append("    </head>\r\n").append("    <body>\r\n")
				.append("<form action=\"multiValuePartUploadServlet\" method=\"POST\" enctype=\"multipart/form-data\">\r\n")
				.append("Choose a file: <input type=\"file\" name=\"multiValuePartUploadServlet\"/>")
				.append("<input type=\"submit\" value=\"Upload\"/>\r\n").append("</form>").append("</body>\r\n")
				.append("</html>\r\n");
	}

	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
		}
		return Constants.DEFAULT_FILENAME;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uploadPath = getServletContext().getRealPath("") + File.separator + Constants.UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();
		String fileName = "";
		String uploadSucces = "";
		String uploadFailure = "";
		try {

			for (Part part : request.getParts()) {
				fileName = getFileName(part);
				part.write(uploadPath + File.separator + fileName);
			}
			uploadSucces = "File " + fileName + " has uploaded successfully!";
		} catch (FileNotFoundException fne) {
			uploadFailure = "There was an error: " + fne.getMessage();
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append("    <head>\r\n")
				.append("        <title>Upload Servlet</title>\r\n").append("    </head>\r\n").append("    <body>\r\n")
				.append("        <h2>Your Results</h2>\r\n");

		if (uploadSucces.length() > 0)
			writer.append(uploadSucces + "\r\n");
		else {
			writer.append(uploadFailure + "\r\n");
		}

		writer.append("</body>\r\n").append("</html>\r\n");

	}
}
