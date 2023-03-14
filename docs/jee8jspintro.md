# 12 - Expanding on our JSP knowledge in Jakarta JEE 8

##### [JEE 8 JSP introduction Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jsp-is-html-start)

#### 1. We learn about the JSP page directive at the top of the page

##### By comparing html with jsp - in our web archetype copy the index.jsp to sample.jsp and remove the first line

From our previous lesson where we converted the Stream output of a Servlet to a JSP 

(page directive)

	<%@ page contentType="text/html;charset=UTF-8" language="java" %>

language specifies the scripting language to be used in a JSP, java is the default

contentType specifies the response header value to answer a request

	response.setContentType("text/html;charset=UTF-8")

same as

	response.setContentType("text/html");

	response.setCharacterEncoding("UTF-8");

and

	response.setHeader("text/html;charset=UTF-8")

in the MultivalueParameterServlet

#### 2. Directives, Declarations, Scriptlets and Expressions

<!-- A directive such as "page" specifies an action -->

	<%@ a directive looks like this %> 

	<!-- A declaration are used to declare instance variables, methods, inner classes -->

	<%! This is a declaration %>

	<!-- A scriptlet is java code copied to the jspService method -->

	<!-- therefore scope is thus method level and not global as above-->

	<% this is a scriptlet %>

	<!-- A return value to be displayed in the page from an expression -->

	<!-- the scope is also method level as above-->

	<%= this is an expression %>

We will now put an example of all of the above together and examine its lifecycle in Tomcat 9

	example.jsp

and look at the *example.java file for a better understanding

#### 3. JSP Code Comments

		<!-- HTML/XML Comments will be output to the page-->
	
		<!-- HTML/XML Comments will be show : <%= calculate.value() %>-->
	
		<%
		
		//Regular Java Comment in scriptlet
		
		/* Or commented out
		
		java code */
		
		%>
		
		<%-- JSP Comment that wont be output to the page--%>

#### 4. Java Package/Class imports in JSP

	<% page import="java.util.ArrayList, java.io.Exception" %>
	
	<!-- You can separate or combine the imports and all other page directives-->

#### 5. JSP directives

	<%@ a directive looks like this %>

	pageEncoding <!--For character encoding -->

	session <!--Default is true JSP with access to session variable -->

	isELIgnored <!--Default is true, parses Expression Language in JSP -->

	buffer, autoFlush <!--Default is 8kb and true specifies wether JSP output should be buffered-->

	errorPage <!--Specifies the location of JSP Error Page -->

	isErrorPage <!--Specifies the JSP as an Error Page -->

	isThreadSafe <!--Default is true, specifies that the JSP can handle multiple requests-->

#### 5. Including other JSPs

	<%@ include file="/relative/file/path/index.jsp" %>

Include directive as above with relative file path to jsp directory the translated include JSP gets folded into the JSP

To demonstrate

1. Create demo includer.jsp in web root

2. Compile and debug the app

3. Navigate to the translated JSP in the Eclipse workspace (this will demonstrate that it is the same as index.jsp)

##### JSP's can also be included dynamically at run time

	<jsp:include page="/relative/file/path/index.jsp" >

Here the included JSP is compiled separately and at run time the request is forwarded to the dymanically

included JSP

To demonstrate

1. Create dynamicIncluder.jsp in web root

2. Compile and debug

3. Navigate to the translated JSP and investigate the line of code that demonstrates this

As a self exercise think about the pro's an cons of each case

(Here I can incorporate an online quiz)

#### 6. Including Tag Libraries

	<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

As in user.jsp here uri specifies the the URI namespace of the the tag library and the prefix is an

alias you use to refer to the tags in the JSP

Check in the end git branch of this slide show

##### [JEE 8 JSP introduction Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jsp-is-html-end)