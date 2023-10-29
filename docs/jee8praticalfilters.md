# 5. Investigating Practical Uses For Filters

In this section, we will explore practical use cases for filters in web development. We'll focus on two specific filters:

- **Charity Compression-Filter & Charity Logging-Filter** ([GitHub Repo](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/tree/jee8web-filters))

These filters serve the purpose of logging and compression in web applications. Let's delve into each of them:

## 1. The Compression Filter
The [CompressionFilter.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/CompressionFilter.java) is a critical component of this system. It is responsible for compressing the response data for efficient data transfer.

## 2. The Logging Filter
On the other hand, we have the [RequestLogFilter.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/RequestLogFilter.java). This filter plays a crucial role in logging various aspects of incoming requests and responses. It is the first filter in the filter chain and is designed to capture valuable information such as IP addresses, request methods, and any errors that may occur during the process.

However, it's important to note that this logging filter may not work with asynchronous processes.

## Implementation Details
To understand how these filters are set up in the application, we refer to [Configurator.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/Configurator.java). This component manages the integration of these filters into the system.

### Response Wrapping
One notable technique used is response wrapping. This approach involves encapsulating the response object to manage the flow of data efficiently. Response data is initially written to a GZipOutputStream for compression. When the request is complete, the compression is finalized, and the compressed output is written to the wrapped ServletOutputStream. The Responsewrapper also overrides methods like `setContentLength()` and `setContentLengthLong()` since the content length can't be determined in advance due to compression.

This wrapper pattern is commonly used in Java Web Filters, particularly for responses like the compression example we're discussing.

## Practical Applications of Filters
Filters like these are widely employed in web development for a variety of purposes, including:

- **Request Encryption and Decryption:** Filters can be used to secure data during transmission.
- **Authentication and Authorization:** Filters are essential for controlling access to resources and services based on user permissions.
- **Logging and Debugging:** Filters, like the RequestLogFilter, provide valuable insights for monitoring and troubleshooting web applications, especially in cloud-based microservices environments.

## Running the Web Application
To experience these filters in action, you can launch and run the web application on Tomcat 9. Access it through the following URLs:

- [http://localhost:8080/charity-charity-registration/](http://localhost:8080/charity-compression-filter/)
- [http://localhost:8080/charity-charity-registration/servlet](http://localhost:8080/charity-compression-filter/servlet)

For a more in-depth analysis, you can use your browser's development tools to inspect content headers and other relevant information.

This document provides an overview of how filters can be used in web development to enhance functionality and security.