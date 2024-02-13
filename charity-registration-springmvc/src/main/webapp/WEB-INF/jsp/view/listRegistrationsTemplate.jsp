<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<template:basic htmlTitle="Registered Charities" bodyTitle="Registered Charities">
    <c:choose>
        <c:when test="${fn:length(charityRegistrationDatabase) == 0}">
            <i>There are no registrations in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${charityRegistrationDatabase}" var="entry">
                Registration <a href="<c:url value='/charityRegistrationServlet'>
                    <c:param name='action' value='view' />
                    <c:param name='registrationId' value='${entry.key}' />
                </c:url>">
                    <c:out value="${entry.key}" />
                </a>: 
                <a href="<c:url value='/charityRegistrationServlet'>
                    <c:param name='action' value='view' />
                    <c:param name='registrationId' value='${entry.key}' />
                </c:url>">
                    <c:out value="${entry.value.userName}" />
                </a>
                (User Name: <c:out value="${entry.value.userName}" />)<br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</template:basic>
