<%--@elvariable id="timestamp" type="long"--%>
<%--@elvariable id="numberOfSessions" type="int"--%>
<%--@elvariable id="sessionList" type="java.util.List<javax.servlet.http.HttpSession>"--%>
<spring:message code="title.sessionList" var="sessionTitle" />
<template:basic htmlTitle="${sessionTitle}" bodyTitle="${sessionTitle}">
    There are a total of ${numberOfSessions} active sessions in this
    application.<br /><br />
    <c:forEach items="${sessionList}" var="s">
         <c:out value="${s.id} - ${s.getAttribute('com.nicordesigns.user.principal')}" />
        <c:if test="${s.id == pageContext.session.id}">&nbsp;(you)</c:if>
        &nbsp;- last active
        ${nicordesigns:timeIntervalToString(timestamp - s.lastAccessedTime)} ago<br />
    </c:forEach>
</template:basic>
