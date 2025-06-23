<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<spring:message code="title.login" var="loginTitle" />
<template:loggedOut htmlTitle="Log In" bodyTitle="${loginTitle}">

    <spring:message code="message.login.instruction" />
    <br /><br />

    <c:if test="${param.error == 'true' || loginFailed}">
        <b><spring:message code="error.login.failed" /></b>
        <br /><br />
    </c:if>

    <c:if test="${param.expired == 'true'}">
        <b><spring:message code="error.session.expired" /></b>
        <br /><br />
    </c:if>

    <c:if test="${logoutSuccess}">
        <b><spring:message code="message.logout.success" /></b>
        <br /><br />
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label for="username"><spring:message code="field.login.username" /></label><br />
        <input type="text" id="username" name="username" value="${param.username}" />
        <br /><br />

        <label for="password"><spring:message code="field.login.password" /></label><br />
        <input type="password" id="password" name="password" />
        <br /><br />

        <input type="submit" value="<spring:message code='field.login.submit' />" />
    </form>

</template:loggedOut>