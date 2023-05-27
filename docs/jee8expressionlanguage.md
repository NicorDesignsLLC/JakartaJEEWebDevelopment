### Jakarta JEE 8 Web Expression Language


##### [Jakarta Expression Language Start Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-session-management-start)

#### 1. Understanding Expression Language

In order to properly separate the Front End Presentation Layer from the Back End Bussiness Layer it is strongly discouraged to use embedded Java code in JSPs. 
For presentation layer logic such as iterating through a list we will use the Expression Language


##### Introducing The Jakarta EL 3.0 (Jakarta EE 8)

[EL 3.0 Documentation:](https://jakarta.ee/specifications/expression-language/3.0/)

[EL 3.0 Specification:](https://jakarta.ee/specifications/expression-language/3.0/expression-language-spec-3.0.html)

[Jakarta EL 3.0 Maven Co-ordinates](https://search.maven.org/artifact/jakarta.el/jakarta.el-api/3.0.3/jar)

Which falls or is related to the 
[Jakarta JSP 2.3 Specification:](https://jakarta.ee/specifications/pages/2.3/)

##### A look at Evaluation Expressions (EL 4.0 documentation)

[https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#eval-expression](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#eval-expression)

From the official documentation:

"For instance, by convention the Jakarta EE web tier specifications use the `${expr}` construct for immediate evaluation and the `#{expr}` construct for deferred evaluation. This difference in delimiters points out the semantic differences between the two expression types in the Jakarta EE web tier. Expressions delimited by `#{}` are said to use “deferred evaluation” because the expression is not evaluated until its value is needed by the system. Expressions delimited by `${}` are said to use “immediate evaluation” because the expression is compiled when the JSP page is compiled and it is executed when the JSP page is executed. More on this in [Section 1.2.4, “Syntax restrictions”](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#syntax-restrictions)."

One caveat here is that many Javascript frameworks such as JQuery uses the same syntax and to resolve this issue you have to use the HTML Escape or better yet use the 
 jsp-property-group> and deferred-syntax-allowed as literal.


##### Where can you put Expressions in a JSP

You can place Expressions anywhere in a JSP except inside a directive such as 

			<%@ page >
or within a declaration such as
			
			<%= >
because these are pre-compiled with JSP
An example that you've already seen is within text such as Hello User

			Hello ${expr}

that will display the users name.

You can also put expressions inside HTML and JSP tags for assigning attribute values as text color , font etc. You can also use the inside inline javascript and Cascading Style Sheets


#### 2. Writing (code) with the EL Syntax


Expression language like javascript is loosely typed but it still adheres to a rigourus syntax. Expressions always has to evaluate to a value and you can assign the expression to a value

##### [Reserved words in 4.0](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#reserved-words) 

These reserved words more or less perform the same functions as you are used to in Java 

##### [Literal Expressions in 4.0](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#literal-expression)

Both are basically the same as you are used to in Java

##### [Operator Precedence in 4.0](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#operator-precedence)

Note the second to last Lambda Expression which also appeared in Java 8

#####[Literal Values in 4.0](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#literals)

Strings can have both double quotes and single quotes as in Javascript, you much take care when using strings in HTML and JSP tag attributes tough because you can end up wasting a day or more with weird syntax errors in your JSP. 
When dealing with special characters in non-english languages you will also often have to use the escape syntax.
Another thing to note is that numeric values are not explicity typed like Java but dynamically typed.

##### [Collections Construction:](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#construction-of-collection-objects)

##### [Collection Operations:](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#collection-operations)

##### [Resolution of Model Objects and their Properties or Methods:](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#resolution-of-model-objects-and-their-properties-or-methods)

##### [EL Functions:](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#functions)

Not to be confused by [Lambda Expressions](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#functions-2) which are also known as Functions and documented as such



##### [API DOC](https://jakarta.ee/specifications/expression-language/3.0/apidocs/)


##### [Static Field and Method Reference](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#static-field-and-method-reference)


##### [Enums](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#enums)

Can be mapped from Java to the expression language:

##### [Jakarta EL Lambda Expressions](https://jakarta.ee/specifications/expression-language/4.0/jakarta-expression-language-spec-4.0.html#enums)




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
				
##### [Jakarta Expression Language Finish Branch](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-expression-language-finish)

    

