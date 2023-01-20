## Using a servlet to upload files to a new JEE 8 Web App Module

Use these to update these code examples:

https://www.baeldung.com/upload-file-servlet

https://github.com/eugenp/tutorials/tree/master/javax-servlets/src/main/java/com/baeldung/servlets



##### [JEE 8 File Upload Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-file-upload-start)

### 1. Presenting the Charity Tax Document Registration project

#### Our Example Use Case

#### We run an online database of government registered Charities and Non-Profits in South Africa

#### Our example project will allow charities to "register" with us and send is their official tax registration forms 

##### Charities should be able to register their information online and provide attached files such as non-profit tax registration status and address details
##### We will also provide a "ChatBot" feature to guide potential charities who want to register with us
##### South Africa has 11 official languages and our example project will have to be able to support all of them
##### Because we handle sensitive information our example project will have to be really secure
 


### 2. Our example project starting layout

###### One Servlet - Three pages  

	1. A list of registrations - with a link to download a registration file
	2. A page to register - POST down-load-able Tax Registration Files
	3. A page to view a registration
	
### 3. Preparing our Servlet for uploading files
	
###### Three classes  
	
	Attachment - file object
	Registration - registration object

	RegistrationServlet - business object
	

	
	@WebServlet(
        name = "registrationServlet",
        urlPatterns = {"/registrations"},
        loadOnStartup = 1
	)
	
	@MultipartConfig(
	        fileSizeThreshold = 5_242_880, //5MB
	        maxFileSize = 20_971_520L, //20MB
	        maxRequestSize = 41_943_040L //40MB
	)
	
	public class RegistrationServlet extends HttpServlet
	{
	   private static final long serialVersionUID = 4441314868375700166L;
	
		private volatile int REGISTRATION_ID_SEQUENCE = 1;
	
	   private Map<Integer, Registration> registrationDatabase = new LinkedHashMap<>();

@MultipartConfig Annotation specifies file upload parameters

fileSizeThreshold - size of file before written to a local temp directory

maxFileSize, maxRequestSize limit the file size	

For external configuration we can use the web.xml configuration file instead of annotations


### 4. The logic for uploading files in our Servlet


	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
                this.showRegistrationForm(response);
                break;
            case "view":
                this.viewRegistration(request, response);
                break;
            case "download":
                this.downloadAttachment(request, response);
                break;
            case "list":
            default:
                this.listRegistrations(response);
                break;
        }
    }

We use the [Command Pattern](https://en.wikipedia.org/wiki/Command_pattern) to encapsulate our business logic in the doGet() method.

We do the same in the doPost() method.

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        if(action == null)
            action = "list";
        switch(action)
        {
            case "create":
                this.createRegistration(request, response);
                break;
            case "list":
            default:
                response.sendRedirect("registrations"); //Catch All
                break;
        }
    }
    
In the downloadAttachment() method    
    
     response.setHeader("Content-Disposition",
                "attachment; filename=" + attachment.getName());
     response.setContentType("application/octet-stream");

     ServletOutputStream stream = response.getOutputStream();
     stream.write(attachment.getContents());
     
     

TODO : Make these more robust from Baeldungs example

We specify that a file has to be downloaded, we use binary stream to write the file out 

### 5. The logic for uploading files in our Servlet
    

	private void createRegistration(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Registration registration = new Registration();
		
		HttpSession session = request.getSession();
		registration.setUserName((String) session.getAttribute("username"));
		
		registration.setSubject(request.getParameter("subject"));
		registration.setBody(request.getParameter("body"));

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

This method gets the InputStream from the request and copies it to the Attachment object and assigns
it a name 
 
    private Attachment processAttachment(Part filePart)
            throws IOException
    {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while((read = inputStream.read(bytes)) != -1)
        {
            outputStream.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }
	
	
	
Check in the end git branch of this slide show 
##### [JEE 8 File Upload Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-file-upload-finish)

    

