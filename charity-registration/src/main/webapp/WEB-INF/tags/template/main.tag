<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nicordesigns" uri="http://www.nicordesigns.com/jsp/tld/nicordesigns" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>


<!DOCTYPE html>
<html>
    <head>
        <title>Charity Registration :: <c:out value="${fn:trim(htmlTitle)}" /></title>
        <link rel="stylesheet" href="<c:url value='/resources/css/main.css' />" />
        <jsp:invoke fragment="headContent" />
    </head>
    <body>
        <h1>Charity Registration Web Application</h1>
        <table border="0" id="bodyTable">
            <tbody>
                <tr>
                    <td class="sidebarCell">
                        <jsp:invoke fragment="navigationContent" />
                    </td>
                    <td class="contentCell">
                        <h2><c:out value="${fn:trim(bodyTitle)}" /></h2>
                        <jsp:doBody />
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
