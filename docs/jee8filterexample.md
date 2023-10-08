# 4. Exploring Filter Order with a Simple Example

Have a look at the following Servlet code:

[ServletOne.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/ServletOne.java)


[ServletTwo.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/ServletTwo.java)

[ServletThree.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/ServletThree.java)

Filter code as well:

[FilterA.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/FilterA.java)

[FilterB.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/FilterB.java)

[FilterC.java](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/java/com/nicordesigns/filters/FilterC.java)

We see how the filters are mapped in the deployment descriptor here:

[web.xml](https://github.com/NicorDesignsLLC/JakartaJEEWebDevelopment/blob/jee8web-filters/charity-registration/src/main/webapp/WEB-INF/web.xml)

We will now compile and run this web app example: 

Go to:

[http://localhost:8080/charity-registration/ServletOne](http://localhost:8080/charity-charity-filter-order/ServletOne)


[http://localhost:8080/charity-registration/ServletTwo](http://localhost:8080/charity-charity-filter-order/ServletTwo)
?

[http://localhost:8080/charity-registration/ServletThree](http://localhost:8080/charity-charity-filter-order/ServletThree)
?

Play around with the order to find out how it works

