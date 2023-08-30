<template:basic htmlTitle="Registered Charities" bodyTitle="Registered Charities">
        <c:choose>
            <c:when test="${fn:length(charityRegistrationDatabase) == 0}">
                <i>There are no registrations in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${charityRegistrationDatabase}" var="entry">
                    Registration ${entry.key}: <a href="<c:url value="/charityRegistrationServlet">
                        <c:param name="action" value="view" />
                        <c:param name="registrationId" value="${entry.key}" />
                    </c:url>">
                    <c:out value="${entry.value.subject}" /></a>
                    (User Name: <c:out value="${entry.value.userName}" />)<br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
</template:basic>