<%--@elvariable id="registrationDatabase" type="java.util.Map<Integer, com.nicordesigns.site.Registration>"--%>
<spring:message code="title.registrationList" var="listTitle" />
<template:basic htmlTitle="${listTitle}" bodyTitle="${listTitle}">
    <c:choose>
        <c:when test="${fn:length(registrationDatabase) == 0}">
            <i><spring:message code="message.registrationList.none" /></i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${registrationDatabase}" var="entry">
                <spring:message code="message.registrationList.registration" />&nbsp;${entry.key}:
                Registration ${entry.key}:
                <a href="<c:url value="/registration/view/${entry.key}" />">
                <c:out value="${nicordesigns:abbreviateString(entry.value.subject, 60)}"/>
                </a><br />
                <c:out value="${entry.value.userName}" /> 
                <spring:message code="message.registrationList.created" />&nbsp;
                created Registration
                <nicordesigns:formatDate value="${entry.value.dateCreated}" type="both"
                                 timeStyle="short" dateStyle="medium" /><br />
                <br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</template:basic>
