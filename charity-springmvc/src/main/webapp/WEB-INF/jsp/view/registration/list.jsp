<%--@elvariable id="registrationDatabase" type="java.util.Map<Integer, com.nicordesigns.site.Registration>"--%>
<template:basic htmlTitle="Registrations" bodyTitle="Registrations">
    <c:choose>
        <c:when test="${fn:length(registrationDatabase) == 0}">
            <i>There are no Registrations in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${registrationDatabase}" var="entry">
                Registration ${entry.key}:
                <a href="<c:url value="/registration/view/${entry.key}" />">
                <c:out value="${nicordesigns:abbreviateString(entry.value.subject, 60)}"/>
                </a><br />
                <c:out value="${entry.value.userName}" /> created Registration
                <nicordesigns:formatDate value="${entry.value.dateCreated}" type="both"
                                 timeStyle="short" dateStyle="medium" /><br />
                <br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</template:basic>
