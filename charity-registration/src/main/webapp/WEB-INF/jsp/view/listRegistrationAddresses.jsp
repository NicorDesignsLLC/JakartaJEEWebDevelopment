<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- We include the tag libraries because Eclipse as yet can not pick up the base.jspf imports set up in the web.xml -->
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.browser" /></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"/>
    <!-- Custom CSS file for the color scheme -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/custom-style.css"/>
    <script src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <h2 class="display-4 bg-primary custom-heading">
            <fmt:message key="title.page" />
        </h2>
        <c:choose>
            <c:when test="${fn:length(addresses) == 0}">
                <i><fmt:message key="message.noContacts" /></i>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <td><div class="custom-text"><fmt:message key="label.Name" /></div></td>
                                    <td><div class="custom-text"><fmt:message key="label.WebURL" /></div></td>
                                    <td><div class="custom-text"><fmt:message key="label.PhoneNumber" /></div></td>
                                    <td><div class="custom-text"><fmt:message key="label.registrationday" /></div></td>
                                    <td><div class="custom-text"><fmt:message key="label.creationDate" /></div></td>
                                </tr>
                            </thead>
                            <c:forEach items="${addresses}" var="charity">
                                <c:set var="classSuccess" value="" />
                                <tr class="${classSuccess}">
                                    <td><b> <c:out value="${charity.charityName}, ${charity.charityId}" />
                                    </b> <br /></td>
                                    <td>
                                        <div class="form-group">
                                            <a href="<c:out value="${charity.webAddress}" />" class="btn btn-link">
                                                <c:out value="${charity.webAddress}" />
                                            </a>
                                        </div>
                                        <br />
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <c:out value="${charity.phoneNumber}" />
                                        </div>
                                        <br />
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <c:if test="${charity.registrationday != null}">
                                                <fmt:formatDate pattern="dd-MM-yyyy" value="${charity.registrationday}" />
                                            </c:if>
                                        </div>
                                        <br />
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <fmt:parseDate value="${charity.dateCreated}" type="date" pattern="yyyy-MM-dd" var="parsedDate" />
                                            <fmt:formatDate value="${parsedDate}" type="date" pattern="dd.MM.yyyy" var="stdDatum" />
                                            <c:out value="${stdDatum}" />
                                        </div>
                                        <br />
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>