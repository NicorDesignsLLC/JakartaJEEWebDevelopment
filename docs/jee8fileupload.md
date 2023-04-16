## Using a servlet to upload files to a JEE 8 Web App Module




##### [JEE 8 File Upload Start Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-file-upload-start)

### 1. Configuring a Servlet for File Uploads

#### Using Jakarta EE @MultipartConfig
##### We specify the File size parameters

			@WebServlet(name = "MultiValuePartUploadServlet", urlPatterns = { 
			"/multiValuePartUploadServlet" })
			
			@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, 
			maxRequestSize = 1024 * 1024 * 5 * 5)


### 2. Our Servlet generated File Upload Input Form

##### We generate the form using the enctype=”multipart/form-data” attribute to signal a multipart upload.



			@Override
			
			protected void doGet(HttpServletRequest request, HttpServletResponse 
			response)
	 
			  throws ServletException, IOException {
			
			response.setContentType("text/html");
			
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter writer = response.getWriter();
			
			writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append("
			<head>\r\n")
			
			.append("<title>Upload Servlet</title>\r\n").append(" </head>\r\n").append(" 
			<body>\r\n")
			
			.append("<form action=\"multiValuePartUploadServlet\" method=\"POST\" 
			enctype=\"multipart/form-data\">\r\n")
			
			.append("Choose a file: <input type=\"file\" 
			name=\"multiValuePartUploadServlet\"/>")
			
			.append("<input type=\"submit\" value=\"Upload\"/>\r\n").append("
			</form>").append("</body>\r\n")
			
			.append("</html>\r\n");
			
			}

### 3. We specify the location on the server where the file will be uploaded to.

##### We use a Servlet Context Relative path 

				@Override
				
				protected void doPost(HttpServletRequest request, HttpServletResponse response)
				
				throws ServletException, IOException {
				
				// Our Servlet Context Relative Path
				String uploadPath = getServletContext().getRealPath("") + File.separator + 
				Constants.UPLOAD_DIRECTORY;
				
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
				
				uploadSucces = "File " + fileName + " has uploaded successfully!\n";
				
				uploadSucces = uploadSucces + "File upload directory: " + uploadPath;
				
				} catch (FileNotFoundException fne) {
				
				uploadFailure = "There was an error: " + fne.getMessage();
				
				}
				
				response.setContentType("text/html");
				
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter writer = response.getWriter();
				
				writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append(" <head>\r\n")
				
				.append(" <title>Upload Servlet</title>\r\n").append(" </head>\r\n").append("
				 <body>\r\n")
				
				.append(" <h2>Your Results</h2>\r\n");
				
				if (uploadSucces.length() > 0)
				
				writer.append(uploadSucces + "\r\n");
				
				else {
				
				writer.append(uploadFailure + "\r\n");
				
				}
				
				writer.append("</body>\r\n").append("</html>\r\n");
				
				}


### 3. We run our sample Upload Servlet

##### We find the location of our uploaded file

	
##### [JEE 8 File Upload Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-file-upload-finish-1)

    



