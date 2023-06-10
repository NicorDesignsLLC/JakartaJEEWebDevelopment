# Replacing embedded Java in Java Server Pages with Custom JSP Tags


We create a Custom Tag example following along with the official Oracle documentation:	

#[Understanding and Creating Custom JSP Tags](https://docs.oracle.com/cd/E60665_01/as111170/TAGLB/quickstart.htm#TAGLB118)

# JSP Custom Tags Tutorial

### 1.  Project Creation


First we create a [charity-custom-tags](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-custom-tags) example project using the Maven Web Archetype Template 

Then we update the following in the web.xml deployment descriptor:

	 <scripting-invalid>true</scripting-invalid>
	 
this will disable the compiling of embedded Java Code in the JSPs	 
	
We expand our Maven dependencies to make use of an Open Source Apache String manipulation
package here:

		<dependency>
		    <groupId>org.apache.commons</groupId>
		    <artifactId>commons-lang3</artifactId>
		    <version>3.12.0</version>
		    <scope>compile</scope>
		</dependency>

so we will have to add this dependency to our charity registration example.

### 2.  JSP Custom Tags

We want to be able to format a display date in a variety of ways depending on our International User base

wherein we create the **formatDate** tag in the [nicordesigns.tld](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-custom-tags/src/main/webapp/WEB-INF/tld/nicordesigns.tld) tag library definition file, then we can use it in our JSP page as follows:

**Dates View** [dates.jsp](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-custom-tags/src/main/webapp/WEB-INF/jsp/view/dates.jsp)

### 3.  JSP Custom Tag Handler

The back-end or supporting code for our **formatDate** tag will be an implementation of the [javax.servlet.jsp.tagext.TagSupport](https://javaee.github.io/javaee-spec/javadocs/javax/servlet/jsp/tagext/TagSupport.html) class that override the important methods.

We override **doEndTag** to get a custom formatted date.

**TagSupport** provide methods through which we can get JspWriter object and write data to the response. 
We will generate the formatted string using **formatDate.java** and then write it to response. The final implementation is given below.

[FormatDateTag.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-custom-tags/src/main/java/com/nicordesigns/tag/FormatDateTag.java)


Then we copy the [FormatDateTag](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-custom-tags/src/main/java/com/nicordesigns/tag/FormatDateTag.java) class and the [nicordesigns.tld](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-custom-tags/src/main/webapp/WEB-INF/tld/nicordesigns.tld) file from  our [charity-custom-tags](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-custom-tags) project to our [charity-registration](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-registration) project.

We add another function to the TLD by first creating a new [TimeUtils.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/java/com/nicordesigns/TimeUtils.java) Java utility helper class.

Then we expand the [nicordesigns.tld](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/webapp/WEB-INF/tld/nicordesigns.tld) to make use of this function.

We update the [base.jspf](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/webapp/WEB-INF/jsp/base.jspf) file with the new required taglib libraries. 
  
#Now we start work on our custom tag files:

First [main.tag](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/webapp/WEB-INF/tags/template/main.tag) , notice the headContent and navigationContent.

Then [loggedout.tag](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/webapp/WEB-INF/tags/template/loggedOut.tag) 

and also [basic.tag](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/webapp/WEB-INF/tags/template/basic.tag)
Notice that main tags forms the base and the other tags build up on that base, and that we make use of CSS files

We also expand the [Registration.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/java/com/nicordesigns/Registration.java) class and the [RegistrationServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-registration/src/main/java/com/nicordesigns/RegistrationServlet.java)


Then we test and run our web application with the updated custom tags:
 
	http://localhost:8080/charity-registration/login
 	 
We register a couple of charities and ensure that everything runs as expected.



