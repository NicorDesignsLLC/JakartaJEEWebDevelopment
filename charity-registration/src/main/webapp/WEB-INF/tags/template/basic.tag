<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="extraHeadContent" fragment="true" required="false" %>
<%@ attribute name="extraNavigationContent" fragment="true" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nicordesigns" uri="http://www.nicordesigns.com/jsp/tld/nicordesigns" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>


<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>
    <jsp:attribute name="navigationContent">
        <a href="<c:url value="/charityRegistrationServlet" />">List Registered Charities</a><br />
        <a href="<c:url value="/charityRegistrationServlet">
            <c:param name="action" value="create" />
        </c:url>">Register a Charity</a><br />
        <a href="javascript:void 0;"
           onclick="newChat();">Launch Chat Window</a><br />
        <a href="<c:url value="/charityRegistrationChat">
            <c:param name="action" value="list" />
        </c:url>">View Chat Requests</a><br />
        
        <a href="<c:url value="/sessions" />">List Sessions</a><br />
        <a href="<c:url value="/login?logout" />">Log Out</a><br />
        <jsp:invoke fragment="extraNavigationContent" />
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main>