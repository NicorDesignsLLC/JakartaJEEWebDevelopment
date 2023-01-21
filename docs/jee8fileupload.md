## Using a servlet to upload files to a JEE 8 Web App Module




##### [JEE 8 File Upload Start Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-file-upload-start)

### 1. Configuring a Servlet for File Uploads

#### Using Jakarta EE @MultipartConfig

			@WebServlet(name = "MultiValuePartUploadServlet", urlPatterns = { 
			"/multiValuePartUploadServlet" })
			
			@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, 
			maxRequestSize = 1024 * 1024 * 5 * 5)


### 2. Our Servlet generated File Upload Input Form



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

#### We define the form using the enctype=”multipart/form-data” attribute to signal a multipart upload.

	
##### [JEE 8 File Upload Finish Branch](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-file-upload-finish-1)

    



