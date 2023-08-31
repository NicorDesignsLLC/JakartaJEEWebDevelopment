<%--@elvariable id="registrationId" type="java.lang.String"--%>
<%--@elvariable id="registration" type="com.nicordesigns.Registration"--%>
<template:basic htmlTitle="${registration.subject}"
	bodyTitle="Registration #${registrationId}: ${registration.subject}">


	<i><c:out value="${registration.userName}" /></i>
	<br />
	Created <nicordesigns:formatDate value="${registration.dateCreated}"
		type="both" timeStyle="long" dateStyle="full" />
	</i>
	<br />
	<br />
	<br />

	<c:out value="${registration.body}" />
	<br />
	<br />
	<c:if test="${registration.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${registration.attachments}"
			var="attachment" varStatus="status">
			<c:if test="${!status.first}">, </c:if>
			<a
				href="<c:url value="/registrations">
                    <c:param name="action" value="download" />
                    <c:param name="registrationId" value="${registrationId}" />
                    <c:param name="attachment" value="${attachment.name}" />
                </c:url>"><c:out
					value="${attachment.name}" /></a>
		</c:forEach>
		<br />
		<br />
	</c:if>
</template:basic>

