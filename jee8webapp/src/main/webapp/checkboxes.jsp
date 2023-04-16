<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello World Application</title>
    </head>
    <body>
        <form action="checkboxesSubmit.jsp" method="POST">
            Select the colors you like:<br />
            <input type="checkbox" name="color" value="Yellow" /> Yellow<br />
            <input type="checkbox" name="color" value="Black" /> Black<br />
            <input type="checkbox" name="color" value="Orange" /> Orange<br />
            <input type="checkbox" name="color" value="Red" /> Red<br />
            <input type="checkbox" name="color" value="Blue" /> Blue<br />
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
