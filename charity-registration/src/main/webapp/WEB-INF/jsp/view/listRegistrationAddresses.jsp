<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- We include the tag libraries because Eclipse as yet can not pick up the base.jspf imports set up in the web.xml -->
<!DOCTYPE html>
<html>
    <head>
        <title><fmt:message key="title.browser" /></title>
    </head>
    <body>
        <h2><fmt:message key="title.page" /></h2>
        <c:choose>
            <c:when test="${fn:length(addresses) == 0}">
                <i><fmt:message key="message.noContacts" /></i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${addresses}" var="charity">
                    <b>
                        <c:out value="${charity.charityName}, ${charity.charityId}" />
                    </b><br />
                    <a href="<c:out value="${charity.webAddress}" />">
                        <c:out value="${charity.webAddress}" />
                    </a><br />
                    <c:out value="${charity.phoneNumber}" /><br />
                    <c:if test="${charity.registrationday != null}">
                        <fmt:message key="label.registrationday" />:
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${charity.registrationday}" />
                    	<br />
                    </c:if>
                    <fmt:message key="label.creationDate" />:
                    <fmt:parseDate value="${charity.dateCreated}" type="date" pattern="yyyy-MM-dd" var="parsedDate" />
					<fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy" var="stdDatum" />	    
                    ${stdDatum}<br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>
