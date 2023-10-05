<%--@elvariable id="timestamp" type="long"--%>
<%--@elvariable id="numberOfSessions" type="int"--%>
<%--@elvariable id="sessionList" type="java.util.List<javax.servlet.http.HttpSession>"--%>
<template:basic htmlTitle="Active Sessions" bodyTitle="Active Sessions">
    There are a total of ${numberOfSessions} active sessions in this application.<br /><br />
    <c:forEach items="${sessionList}" var="s">
        <c:set var="sessionIdLabel">Session Id:</c:set>
        <c:set var="usernameLabel">User Name:</c:set>

        <c:out value="${sessionIdLabel} ${s.id}" />
        <c:if test="${s.id == pageContext.session.id}">
            &nbsp;(you)&nbsp;
        </c:if>

        <c:if test="${s.getAttribute('username') ne null}">
            <br />
            <c:out value="${usernameLabel} ${s.getAttribute('username')}" />
        </c:if>
        <br />
    </c:forEach>
</template:basic>
