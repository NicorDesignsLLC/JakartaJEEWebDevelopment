<%-- @elvariable id="registrations" type="java.util.List<com.nicordesigns.site.Registration>" --%>

<spring:message code="title.registrationList" var="listTitle" />
<template:basic htmlTitle="${listTitle}" bodyTitle="${listTitle}">
    <c:choose>
        <c:when test="${fn:length(registrations) == 0}">
            <i><spring:message code="message.registrationList.none" /></i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${registrations}" var="registration">
                <spring:message code="message.registrationList.registration" />&nbsp;${registration.id}:
                Registration ${registration.id}:
                <a href="<c:url value='/registration/view/${registration.id}' />">
                    <c:out value="${nicordesigns:abbreviateString(registration.subject, 60)}"/>
                </a><br />
                <c:out value="${registration.userName}" />&nbsp;
                <spring:message code="message.registrationList.created" />&nbsp;
                <nicordesigns:formatDate value="${registration.dateCreated}" type="both" timeStyle="short" dateStyle="medium" /><br />
                <br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</template:basic>
