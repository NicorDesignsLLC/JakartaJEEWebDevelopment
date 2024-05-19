<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="com.nicordesigns.site.AuthenticationController.Form"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<spring:message code="title.login" var="loginTitle" />
<template:loggedOut htmlTitle="Log In" bodyTitle="${loginTitle}">
		<spring:message code="message.login.instruction" /><br /><br />
        <c:if test="${loginFailed}">
            <b><spring:message code="error.login.failed" /></b><br /><br />    
        </c:if>
        <c:if test="${validationErrors != null}"><div class="errors">
        <ul>
            <c:forEach items="${validationErrors}" var="error">
                <li><c:out value="${error.message}" /></li>
            </c:forEach>
        </ul>
    </div></c:if>
    
    <form:form method="post" modelAttribute="loginForm">
        <form:label path="username"><spring:message code="field.login.username" /></form:label><br />
        <form:input path="username"/><br/><br/>
        <form:errors path="username" cssClass="errors" />
        <form:label path="password"><spring:message code="field.login.password" /></form:label><br />
        <form:password path="password"/><br/><br/>
        <form:errors path="password" cssClass="errors" /><br />
        <spring:message code="field.login.submit" var="submitMessage" />
		<input type="submit" value="${submitMessage}" />
    </form:form>
</template:loggedOut>
