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

<

<c:set var="registrationDatabase" value="${empty requestScope.charityRegistrationDatabase ? null : requestScope.charityRegistrationDatabase}" />

<!DOCTYPE html>
<html>
    <head>
        <title>Charity Registration :: <c:out value="${fn:trim(htmlTitle)}" /></title>
        <link rel="stylesheet" href="<c:url value='/resources/css/main.css' />" />
        
        
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
        
        <script type="text/javascript" lang="javascript">
            var postInvisibleForm = function(url, fields) {
                var form = $('<form id="mapForm" method="post"></form>')
                        .attr({ action: url, style: 'display: none;' });
                for(var key in fields) {
                    if(fields.hasOwnProperty(key))
                        form.append($('<input type="hidden">').attr({
                            name: key, value: fields[key]
                        }));
                }
                $('body').append(form);
                form.submit();
            };
            var newChat = function() {
                postInvisibleForm('<c:url value="/charityRegistrationChat" />', { action: 'new' });
            };
        </script>
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
