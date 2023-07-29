# Internationalization Tags

	<fmt:message>
		
[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtmessage](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtmessage)

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:message](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:message)

	<fmt:setLocale value="en_US" />
	
[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#i18n-localization-context](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#i18n-localization-context)

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#preferred-locales](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#preferred-locales)

	

Resolves a localized message in a resource bundle to display it inline or saves it to a variable , the key specifies the key to resolve to display.

related to local is the Resource Bundle

	<fmt:bundle>
	
[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:bundle](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:bundle)

Explains and expands on how to use the optional bundle attribute that indicates which localization context can be used including <fmt:param>

	<fmt:setBundle>

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:setBundle](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:setBundle)


	<fmt:timeZone>

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:timeZone](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmt:timeZone)

	<fmt:setTimeZone>

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtsettimezone](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtsettimezone)

	<fmt:formatNumber>

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtformatnumber](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtformatnumber)

	<fmt:formatDate>

[https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtformatdate](https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#fmtformatdate)


#### Putting Internationalization  Library Tags to Use 
### Our FMT Library Examples
Charity Address book - we expand on the Charity Registration Example that where we introduced Core Tag Library examples:

first we expand on our web.xml by adding the following context initialization parameter

	<context-param>
        <param-name>javax.servlet.jsp.jstl.fmt.localizationContext</param-name>
        <param-value>CharityAddressBook-messages</param-value>
    </context-param>


[https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/webapp/WEB-INF/web.xml](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/webapp/WEB-INF/web.xml)


this helps to establish a resources bundle that will be used to store all the localized language messages    


The container (Tomcat 9) in our case looks for the resource bundle anywhere on our Java Class Path of our web application
with the following name:

	CharityAddressBook-messages_[language]_[region].properties
	
if it does not find that it looks for
	
	CharityAddressBook-messages_[language].properties
	
and finally if that is not found it switches to a fall back locale US English in my case  
 		

In our case we will follow the option of Maven convention over configuration and place our resource bundle file
in the src/main/resources directory

[CharityAddressBook-messages_en_US.properties](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/resources/CharityAddressBook-messages_en_US.properties)


these files should then be copied to the class path locations during our Maven Build

We add the CharityRegistrationAddressListServlet to use the new language option as follows:

[https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/java/com/nicordesigns/CharityRegistrationAddressListServlet.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/java/com/nicordesigns/CharityRegistrationAddressListServlet.java)


Then we update the index.jsp with a new re-direct and the base.jspf with the new i8n taglib directive

[https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/webapp/WEB-INF/jsp/base.jspf](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/webapp/WEB-INF/jsp/base.jspf)

and we add the listRegistrationAddresses.jsp for internationalization of text and formatting of the dates

[https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/webapp/WEB-INF/jsp/view/listRegistrationAddresses.jsp](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-jstl-part2/charity-registration/src/main/webapp/WEB-INF/jsp/view/listRegistrationAddresses.jsp)


we will compile and run our webapp and have a look at it:

[http://localhost:8080/charity-registration/charityRegistrationAddressListServlet](http://localhost:8080/charity-registration/charityRegistrationAddressListServlet)

and

[http://localhost:8080/charity-registration/charityRegistrationAddressListServlet?language=afrikaans](http://localhost:8080/charity-registration/charityRegistrationAddressListServlet?language=afrikaans)

[http://localhost:8080/charity-registration/charityRegistrationAddressListServlet?language=afrikaans&empty](http://localhost:8080/charity-registration/charityRegistrationAddressListServlet?language=afrikaans&empty)





