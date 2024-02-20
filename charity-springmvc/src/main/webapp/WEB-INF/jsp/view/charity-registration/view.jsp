<%--@elvariable id="RegistrationId" type="java.lang.String"--%>
<%--@elvariable id="Registration" type="com.nicordesigns.site.Registration"--%>
<template:basic htmlTitle="${Registration.subject}"
                bodyTitle="Registration #${RegistrationId}: ${Registration.subject}">
    <i>Customer Name - <c:out value="${Registration.customerName}" /><br />
    Created <nicordesigns:formatDate value="${Registration.dateCreated}" type="both"
                             timeStyle="long" dateStyle="full" /></i><br /><br />
    <c:out value="${Registration.body}" /><br /><br />
    <c:if test="${Registration.numberOfAttachments > 0}">
        Attachments:
        <c:forEach items="${Registration.attachments}" var="attachment"
                   varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/registration/${RegistrationId}/attachment/${attachment.name}" />"><c:out value="${attachment.name}" /></a>
        </c:forEach><br /><br />
    </c:if>
</template:basic>
