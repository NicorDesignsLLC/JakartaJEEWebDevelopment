<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello User Application</title>
    </head>
    <body>
        settingOne: <%= application.getInitParameter("databaseOne") %>,</br>
        settingTwo: <%= application.getInitParameter("cloudOne") %>
    </body>
</html>
