# 4. Exploring Filter Order with a Simple Example

Have a look at the following code:

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/ServletOne.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/ServletOne.java)


[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/ServletTwo.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/ServletTwo.java)

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/ServletThree.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/ServletThree.java)

The same for:

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/FilterA.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/FilterA.java)

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/FilterB.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/FilterB.java)

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/FilterC.java](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/java/com/nicordesigns/FilterC.java)

We see how the filters are mapped in the deployment descriptor here:

[https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/webapp/WEB-INF/web.xml](https://github.com/NicorDesigns/javawebdevcourse/blob/jee8web-filters-end/charity-filter-order/src/main/webapp/WEB-INF/web.xml)

We will now compile and run this web app example: 

Go to:

[http://localhost:8080/charity-charity-filter-order/ServletOne](http://localhost:8080/charity-charity-filter-order/ServletOne)

Tomcat Console Log Output:

?

[http://localhost:8080/charity-charity-filter-order/ServletTwo](http://localhost:8080/charity-charity-filter-order/ServletTwo)
?

[http://localhost:8080/charity-charity-filter-order/ServletThree](http://localhost:8080/charity-charity-filter-order/ServletThree)
?

Play around with the order to find out how it works

