# 5. Investigating Practical Uses For Filters

Here we will be looking at another example: 

[charity-compression-filter](https://github.com/NicorDesigns/javawebdevcourse/tree/jee8web-filters-end/charity-compression-filter)

which shows a logging filter and a compression filter.

The [CompressedServlet.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-compression-filter/src/main/java/com/nicordesigns/CompressedServlet.java) 
are mapped to /servlet and a doGet() response output.

It also has an [index.jsp](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-compression-filter/src/main/webapp/index.jsp) file


This is how the filters are programmatically set up in the app:
[Configurator.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-compression-filter/src/main/java/com/nicordesigns/Configurator.java)


## The logging filter:
[RequestLogFilter.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-compression-filter/src/main/java/com/nicordesigns/RequestLogFilter.java)
Is the first filter in the filter chain?  It times the request process and captures a bunch of information to log: IP Address, Request Method etc. The finally are used to ensure it can also log errors being thrown. A note here this won't work for ASync processes

## The compression filter:

[CompressionFilter.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-compression-filter/src/main/java/com/nicordesigns/CompressionFilter.java)

We wrap the Response Object passed because the Response Data can start flowing back before the Servlet completed the servicing the Request or it can start flowing back after the the Servlet has complted Servicing the Request (Async Request handling).
Response data is first written to a GZipOutputStream and then when the requests completes it finishes the compression and writes the compressed output to the wrapped ServletOutputStream.

The Responsewrapper also overwrites setContentLength() and setContentLengthLong() to prevent the Content Length to be set, because the length can obviously not be known before it is compressed.

The wrapper pattern is commonly used in Java Web Filters more so for Resonses as in our Compression example than for Requests. The common application for filters are for encrypting and decrypting request, for authentication and authorization of access to resources and services and also of cause logging and debugging for an extended set of cloud based micro services.

We launch and run our web application in Tomcat 9

http://localhost:8080/charity-compression-filter/

http://localhost:8080/charity-compression-filter/servlet

now we will use our browsers development tools to look at the Content headers and so forth




