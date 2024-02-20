<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="nicordesigns" uri="http://www.nicordesigns.com/jsp/tld/nicordesigns" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>

<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <link rel="stylesheet"
              href="<c:url value="/resource/stylesheet/login.css" />" />
    </jsp:attribute>
    <jsp:attribute name="navigationContent" />
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main>
