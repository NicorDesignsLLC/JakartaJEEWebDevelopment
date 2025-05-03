<%--@elvariable id="registrationId" type="java.lang.String"--%>
<%--@elvariable id="registration" type="com.nicordesigns.site.Registration"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<spring:message code="title.registrationView" var="viewTitle" />
<template:basic htmlTitle="${registration.subject}"
                bodyTitle="${viewTitle} #${registrationId}: ${registration.subject}">
    <i><spring:message code="message.registrationView.customerName" /> -            
    <c:out value="${registration.userName}" /><br />
    <spring:message code="message.registrationView.created" /> 
    <nicordesigns:formatDate value="${registration.dateCreated}" type="both" timeStyle="long" dateStyle="full" /></i><br /><br />
    <c:out value="${registration.body}" /><br /><br />
    <c:if test="${registration.numberOfAttachments > 0}">
        <spring:message code="message.registrationView.attachments" />:
        <c:forEach items="${registration.attachments}" var="attachment" varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value='/registration/${registrationId}/attachment/${attachment.name}' />"><c:out value="${attachment.name}" /></a>
        </c:forEach><br /><br />
    </c:if>
</template:basic>