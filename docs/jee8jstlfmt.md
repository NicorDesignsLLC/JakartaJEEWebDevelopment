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
Charity Address book - we expand on the Charity Address Book Sample that we introduced in our previous Core Tag Library Examples:

[https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-address-book](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-address-book)

the we expand on our web.xml by adding the following context initialization parameter

	<context-param>
        <param-name>javax.servlet.jsp.jstl.fmt.localizationContext</param-name>
        <param-value>CharityAddressBook-messages</param-value>
    </context-param>

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/WEB-INF/web.xml](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/webapp/WEB-INF/web.xml)


this helps to establish a resources bundle that will be used to store all the localized language messages    



The container (Tomcat) in our case looks for the resource bundle anywhere on our Java Class Path of our web application
with the following name:

	CharityAddressBook-messages_[language]_[region].properties
	
if it does not find that it looks for
	
	CharityAddressBook-messages_[language].properties
	
and finally if that is not found it switches to a fall back locale US English in my case 
 		

[CharityAddressBook-messages_en_US.properties](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-jstl-finish/charity-address-book/src/main/resources/CharityAddressBook-messages_en_US.properties)


In our case we will follow the option of Maven convention over configuration and place our resource bundle file
in the src/main/resources directory


[https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-address-book/src/main/resources](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-jstl-finish/charity-address-book/src/main/resources)

these files should then be copied to the class path locations during our Maven Build

We change the ListServlet to account for a new language option as follows:

[https://github.com/NicorDesigns/javawebdevcourse/commit/985eaae9ff3b466dc29ae2b5d69fb6c5a38ab686#diff-aa4158958031193c4f98f7459da1bfecb75124d211094361d785752a50cb545d](https://github.com/NicorDesigns/javawebdevcourse/commit/985eaae9ff3b466dc29ae2b5d69fb6c5a38ab686#diff-aa4158958031193c4f98f7459da1bfecb75124d211094361d785752a50cb545d)


Then we update base.jspf with the new i8n taglib directive

[https://github.com/NicorDesigns/javawebdevcourse/compare/master...jee8web-jstl-finish#diff-06cce8aedd0b0e920b201bec8be6d2cf86721b68d7996bbe56c34d02103b0d6b](https://github.com/NicorDesigns/javawebdevcourse/compare/master...jee8web-jstl-finish#diff-06cce8aedd0b0e920b201bec8be6d2cf86721b68d7996bbe56c34d02103b0d6b)


and we update the list.jsp to enable internationalization and formatting of the dates

[https://github.com/NicorDesigns/javawebdevcourse/compare/master...jee8web-jstl-finish#diff-12550320c88c0c356aacf0b0b5bf0ae4879b12bbb9f5bec29f439c0922833510](https://github.com/NicorDesigns/javawebdevcourse/compare/master...jee8web-jstl-finish#diff-12550320c88c0c356aacf0b0b5bf0ae4879b12bbb9f5bec29f439c0922833510)


we will compile and run our app and have a look at

[http://localhost:8080/charity-address-book/list](http://localhost:8080/charity-address-book/list)

and

[http://localhost:8080/charity-address-book/list?language=afrikaans](http://localhost:8080/charity-address-book/list?language=afrikaans)

[http://localhost:8080/charity-address-book/list?language=afrikaans&empty](http://localhost:8080/charity-address-book/list?language=afrikaans&empty)





