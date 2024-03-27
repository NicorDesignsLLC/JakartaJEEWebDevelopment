<%--@elvariable id="registrationId" type="java.lang.String"--%>
<%--@elvariable id="registration" type="com.nicordesigns.site.Registration"--%>
<template:basic htmlTitle="${registration.subject}"
                bodyTitle="Registration #{$RegistrationId}:">
    <i>Registration User Name - <c:out value="${registration.userName}" /><br />
    Created <nicordesigns:formatDate value="${registration.dateCreated}" type="both"
                             timeStyle="long" dateStyle="full" /></i><br /><br />
    <c:out value="${registration.body}" /><br /><br />
    <c:if test="${registration.numberOfAttachments > 0}">
        Attachments:
        <c:forEach items="${registration.attachments}" var="attachment"
                   varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/registration/${registrationId}/attachment/${attachment.name}" />"><c:out value="${attachment.name}" /></a>
        </c:forEach><br /><br />
    </c:if>
</template:basic>
