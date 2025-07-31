<%--@elvariable id="registrationId" type="java.lang.String"--%>
<%--@elvariable id="registration" type="com.nicordesigns.site.Registration"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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

    <!-- User Role Information Section -->
    <div class="user-info-panel" style="background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 5px; padding: 15px; margin: 20px 0;">
        <h5><spring:message code="label.userInfo" text="User Information" /></h5>
        <p><strong><spring:message code="label.currentUser" text="Current User" />:</strong> 
           <sec:authentication property="name" /></p>
        
        <p><strong><spring:message code="label.userRoles" text="Your Roles" />:</strong>
        <c:set var="hasAnyRole" value="false" />
        <sec:authorize access="hasRole('ADMIN')">
            <span class="badge badge-danger">ADMIN</span>
            <c:set var="hasAnyRole" value="true" />
        </sec:authorize>
        <!-- Add more roles as needed -->
        <c:if test="${!hasAnyRole}">
            <span class="badge badge-secondary"><spring:message code="label.noRoles" text="No roles assigned" /></span>
            <br><small class="text-muted"><spring:message code="info.contactAdmin" text="Contact your administrator to request appropriate roles." /></small>
        </c:if>
        </p>

        <p><strong><spring:message code="label.permissions" text="Available Actions" />:</strong></p>
        <ul class="list-unstyled">
            <c:choose>
                <c:when test="${hasAnyRole}">
                    <li>✓ <spring:message code="permission.view" text="View registrations" /></li>
                    <sec:authorize access="hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')">
                        <li>✓ <spring:message code="permission.create" text="Create new registrations" /></li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN') or hasRole('MANAGER')">
                        <li>✓ <spring:message code="permission.edit" text="Edit registrations" /></li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <li>✓ <spring:message code="permission.delete" text="Delete registrations" /></li>
                    </sec:authorize>
                    <sec:authorize access="!hasRole('ADMIN')">
                        <li>✗ <span style="color: #6c757d;"><spring:message code="permission.delete.denied" text="Delete registrations (requires ADMIN role)" /></span></li>
                    </sec:authorize>
                </c:when>
                <c:otherwise>
                    <li>✓ <spring:message code="permission.view" text="View registrations" /> <small class="text-muted">(<spring:message code="permission.viewOnly" text="view only - limited access" />)</small></li>
                    <li>✗ <span style="color: #6c757d;"><spring:message code="permission.create.denied" text="Create new registrations (requires USER role or higher)" /></span></li>
                    <li>✗ <span style="color: #6c757d;"><spring:message code="permission.edit.denied" text="Edit registrations (requires MANAGER role or higher)" /></span></li>
                    <li>✗ <span style="color: #6c757d;"><spring:message code="permission.delete.denied" text="Delete registrations (requires ADMIN role)" /></span></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>

    <!-- Delete Button Section -->
    <div style="margin-top: 20px;">
        <sec:authorize access="hasRole('ADMIN')">
            <form action="<c:url value='/registration/delete/${registrationId}' />" method="post" style="display: inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this registration?');">
                    <spring:message code="button.delete" text="Delete" />
                </button>
            </form>
        </sec:authorize>
        
        <sec:authorize access="!hasRole('ADMIN')">
            <div class="alert alert-info" role="alert">
                <strong><spring:message code="info.deleteRestricted" text="Delete Restricted" />:</strong>
                <spring:message code="info.deleteRequiresAdmin" text="Only users with ADMIN role can delete registrations. Contact your administrator if you need to delete this registration." />
            </div>
        </sec:authorize>
        
        <!-- Back to List Button -->
        <a href="<c:url value='/registration/list' />" class="btn btn-secondary">
            <spring:message code="button.backToList" text="Back to List" />
        </a>
    </div>

    <!-- Flash Messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert" style="margin-top: 15px;">
            ${successMessage}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert" style="margin-top: 15px;">
            ${errorMessage}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
</template:basic>