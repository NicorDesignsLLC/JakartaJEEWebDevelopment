# Replacing Embedded Java in Java Server Pages with Custom JSP Tags

## Introduction

In this guide, we'll explore how to replace embedded Java code within Java Server Pages (JSP) using custom JSP tags. If you're a Java Developer transitioning from a different skillset or specialty, this guide will help you understand the process step by step.

## Prerequisites

Before we begin, make sure you have the following prerequisites in place:

1. Basic familiarity with Java programming.
2. Maven installed to manage project dependencies.
3. A code editor of your choice.
4. Java Development Kit (JDK) installed.

## 1. Project Setup

Let's start by setting up a sample project to demonstrate custom JSP tags. Follow these steps:

1. Create a new project named "charity-custom-tags" using the Maven Web Archetype Template.
2. Update the `web.xml` deployment descriptor to disable compiling of embedded Java code in JSPs by adding the line:
   ```xml
   <scripting-invalid>true</scripting-invalid>
   ```
3. Expand the Maven dependencies in your project's `pom.xml` to include the Apache Commons Lang3 library:
   ```xml
   <dependency>
       <groupId>org.apache.commons</groupId>
       <artifactId>commons-lang3</artifactId>
       <version>3.12.0</version>
       <scope>compile</scope>
   </dependency>
   ```

## 2. Creating Custom JSP Tags

Now, let's create a custom JSP tag called "formatDate" to format display dates based on different preferences:

1. Define the "formatDate" tag in the `nicordesigns.tld` tag library definition file.
2. Utilize the tag in a JSP page, such as `dates.jsp`, to display formatted dates.

## 3. Implementing the Custom Tag Handler

The custom tag handler, known as `FormatDateTag`, is the backend code responsible for processing the "formatDate" tag. It's an implementation of the `javax.servlet.jsp.tagext.TagSupport` class. Here's how it works:

1. Override the `doEndTag` method to generate and display a custom formatted date.
2. Utilize `TagSupport` methods to interact with the JspWriter object and write data to the response.
3. Copy the `FormatDateTag` class and the `nicordesigns.tld` file to your main project.

## 4. Enhancing the Tag Library

Let's extend the tag library by adding new functionality:

1. Create a `TimeUtils` Java utility helper class to enhance tag functionality.
2. Update the `nicordesigns.tld` file to include the new function.
3. Update the necessary JSP files, like `base.jspf`, with the required taglib libraries.

## 5. Building Custom Tag Files

We'll now create custom tag files that form the structure of our JSP pages:

1. Design the `main.tag` file, including sections for `headContent` and `navigationContent`.
2. Create `loggedout.tag` and `basic.tag` files, building upon the base structure and utilizing CSS files.

## 6. Adapting the Java Code

Update the Java code to integrate custom tags and improve the overall application:

1. Expand the `Registration` class and `RegistrationServlet` with the updated tag functionality.

## Testing and Deployment

1. Test your web application by accessing: `http://localhost:8080/charity-registration/login`
2. Register a couple of charities to ensure everything functions as expected.

## Conclusion

By following this guide, you've learned how to replace embedded Java code with custom JSP tags. This approach enhances code readability, maintainability, and promotes a more organized structure for your Java web application.

For a detailed guide, refer to the official Oracle documentation on [Understanding and Creating Custom JSP Tags](https://docs.oracle.com/cd/E60665_01/as111170/TAGLB/quickstart.htm#TAGLB118).
