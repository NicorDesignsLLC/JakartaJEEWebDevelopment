### Jakarta JEE 8 Web Expression Language - Part 3


##### [Jakarta Expression Language Part 3 Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-session-management-start)


#### 3. Using scoped variables in EL expressions 

[Implicit EL Variables:](https://jakarta.ee/specifications/webprofile/8/apidocs/javax/servlet/jsp/el/implicitobjectelresolver)

[ELResolver](https://jakarta.ee/specifications/webprofile/8/apidocs/javax/el/elresolver)

Except for the pageContext they are all Map Objecs, 
EL Implicit Variables and Implicit Scope



For this we will look at the user-profile example

[http://localhost:8080/user-profile/info.jsp?user=nicordesigns](http://localhost:8080/user-profile/info.jsp?user=nicordesigns)

[http://localhost:8080/user-profile/info.jsp?user=nicordesigns&colors=green&colors=red](http://localhost:8080/user-profile/info.jsp?user=nicordesigns%colors=green&colors=red)

[http://localhost:8080/user-profile/scope.jsp](http://localhost:8080/user-profile/scope.jsp)

Demonstrates the implicit scope order:

a = page
b = request
c = session
d = application


We re-use web.xml from our previous examples and index.jsp will redirect to the profile servlet

The base.jspf declares the EL function libraries which are brought in by the following Maven dependency: 

		<dependency>
			<groupId>org.glassfish.web</groupId>
			<artifactId>javax.servlet.jsp.jstl</artifactId>
			<version>1.2.5</version>
			<scope>compile</scope>
			<exclusions>
				<exclusion>
					<groupId>javax.servlet</groupId>
					<artifactId>servlet-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.servlet.jsp</groupId>
					<artifactId>jsp-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>javax.servlet.jsp.jstl</groupId>
					<artifactId>jstl-api</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	

##### Scope Overview
		
Request Scope 	- begins when the server receives a request and ends when the response has been sent back from the server.
				- is accessible through the Request Object	

Session Scope	- Is larger than request scope
				- Is accessible through the HttpSession Object
				
Page Scope		- Is available in the JSP (page) only and its request
				- Is accessible through the PageContext and  JSPObject
				
Application Scope - Is globally available in the web application
				  - Is accessible through the ServletContext Object									
		

##### EL Implicit Scope

Can resolve an attribute in all of the above scopes. 

An EL variable is resolved as follows: 
First check if it is an Implicit Variable, then check if it is a Page Scope variable, then check if it is a request scoped variable, then session and finally application. This is done through the related Context Object and its getAttribute method 

We see this in our User Profile code example in how the User Attributes are displayed using EL variables

We first look at the User Class , the Profile Servlet and the profile.jsp code in our example project and we run it.

We change the scopes in the Profile Servlet

			//1. request.setAttribute("user", user);
        	//2. request.getSession().setAttribute("user", user);
        	//3. this.getServletContext().setAttribute("user", user);
        	
#### 4. Stream and Pipeline operations on Collections

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#stream-and-pipeline](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#stream-and-pipeline)

The stream method is present on every Collection in Java. java.util.Arrays also provides many static methods to return streams.

As in the official documentation listed above you can perform many operations on a Stream and build a pipeline.

In the official documentation example the .toList() is known as a terminal operation. 

			filter(b->b.category == 'history’)
			              .map(b->b.title)   
              
is known as intermediate operations			  

and the Stream method is known as the pipeline source

##### Understanding Intermediate Operations

When performing intermediate operations on a Collection with a Stream the original Collection is never altered the contents of the Collection are. 

##### Filtering the Stream

The filter operation accepts a predicate argument which is a lamda expression that returns a boolean and accepts a single argument of the element type of the Stream :

			books.filter(b->b.category == 'history’)
          
The predicate argument (lamda expression): Accept book and test if its category is 'history' and the resulting Stream only contains those books for which the predicate is true. This Stream then is a "reduction" in the content of the List of books

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#example-5](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#example-5)

To remove the duplicate element b:

			['a', 'b', 'b', 'c'].stream().distinct().toArray()

##### Manipulating Values

You can use forEach to manipulate values in a Stream:

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#foreach](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#foreach)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#example-6](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#example-6)

			customers.stream().forEach(c->printer.print(c.name))

Here the Lambda Expression is a consumer 

##### Sorting the Stream

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#sorted](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#sorted)

				Stream<S> Stream<S>.sorted()

				Stream<S> Stream<S>.sorted(((p,q)->int) comparator)

				${books.stream().sorted((b1, b2) -> b1.title.compareTo(b2.title)

				${['a', 'b', 'b', 'c'].stream().sorted()}

##### Limiting the Stream size

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#limit](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#limit)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#substream](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#substream)

##### Transforming the Stream

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#map](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#map)

The map operation transforms the element types of a Stream from one element type to another element type

				products.stream().filter(p->p.unitPrice >= 10)
                 .map(p->[p.name, p.unitPrice])
                 .toList()

##### Using Terminal Operations
				
Terminal operation is when we end the processing of our Stream and return an actual non-Stream Java object

##### Returning a Collection

To return an Array or a List

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#toarray](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#toarray)	

				S[] Stream<S>.toArray()

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#tolist](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#tolist)

				List Stream<S>.toList()
				
##### Using Aggregate Functions

The aggregate functions that you can perform on a Stream is min, max, average, sum and count

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#max](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#max)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#min](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#min)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#average](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#average)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#sum](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#sum)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#count](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#count)


##### Returning the First Value

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#findfirst](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#findfirst)

#### Putting the Stream API to use

We use the Collections.jsp to demonstrate this is in the user-profile example:


First we look at the User construction:

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-expression-language-finish/User-Profile/src/main/java/com/nicordesigns/User.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-expression-language-finish/User-Profile/src/main/java/com/nicordesigns/User.java)

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-expression-language-finish/User-Profile/web/collections.jsp](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-expression-language-finish/User-Profile/web/collections.jsp)
       
#### Replacing the Java Code with the Expression Language in Charity Registration

Update the base.jspf file to contain a tag library declaration
RegistrationForm.jsp has no Java code but viewRegistration.jsp can be updated with EL
So can sessions.jsp

We will run and test the changes
				
##### [Jakarta Expression Language Part 3 Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-expression-language-finish)

    

