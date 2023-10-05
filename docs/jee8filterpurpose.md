## The purpose of filters



Filters can intercept and modify requests and responses and the can reject, redirect or forward incoming requests.

[Jakarta Filter API JavaDoc](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/filter)
and implementations GenericFilter and [HttpFilter](https://jakarta.ee/specifications/platform/8/apidocs/javax/servlet/http/httpfilter)

The Essentials of [Filters according to Oracle](https://www.oracle.com/java/technologies/filters.html)

Filters can be declared in the web.xml deployment descriptor, with java annotations in your code or programmitacaly in Java

Some of the common use cases for filters are

#### Logging Filters

In the new cloud based paradigm logging what happened where to your requests and responses,  are becoming almost crucial to be able to ensure the your web applications performance according to specifactions and within performance parameters. This has seen an explosion of third party libraries and products to have insights into your app.
Yet you will still need to generate the logs the old Java way

#### Authentication Filters
Once again with the advance of 24x7 cloud based Java Applications it is essential to be able to authenticate every single incoming request. In other word is this a valid user. It is also important to authorize every single incoming request in terms of what resources can be accessed. This is all done through filters 

#### Compression and Encryption Filters
When your cloud based Java Web App gets a million plus incoming requests every minute of every day you are going to need to boost performance by whatever means available to you. Using compression filters are just one such available option to you. 
Then also if these incoming request are for monetary transactions you will want to harden your security defenses with all the means availble to you. Encryption and Decription Filters will add an extra layour of protection against the ever present Anarcho Criminal State Sponsored and rogue hackers.

#### Error handling Filters
In the new Cloud based world of AWS, GCP and Azure Java Apps having a comprehensive Error handling Filter that will notify your production support team of backend errors (500 errors) and tell your front end customers nicely that your app are experiencing technical difficulties. Your filter can intercept every error in a try-catch block
