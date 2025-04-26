<%--@elvariable id="registrationId" type="java.lang.String"--%>
<%--@elvariable id="registration" type="com.nicordesigns.site.Registration"--%>
<spring:message code="title.registrationView" var="viewTitle" />
<template:basic htmlTitle="${registration.subject}"
                bodyTitle="${viewTitle}  #${RegistrationId}: ${registration.subject}">
     <i><spring:message code="message.registrationView.customerName" /> -            
     <c:out value="${registration.userName}" /><br />
     <spring:message code="message.registrationView.created" />&nbsp; 
     <nicordesigns:formatDate value="${registration.dateCreated}" type="both"
                             timeStyle="long" dateStyle="full" /></i><br /><br />
    <c:out value="${registration.body}" /><br /><br />
    <c:if test="${registration.numberOfAttachments > 0}">
        <spring:message code="message.registrationView.attachments" />:
        <c:forEach items="${registration.attachments}" var="attachment"
                   varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/registration/${registrationId}/attachment/${attachment.name}" />"><c:out value="${attachment.name}" /></a>
        </c:forEach><br /><br />
    </c:if>
</template:basic>
