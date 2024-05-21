<%@ page isELIgnored="false"%>
<%-- @elvariable id="registrationForm" type="com.nicordesigns.site.RegistrationController.Form" --%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<template:basic htmlTitle="Create a Registration"
	bodyTitle="Create a Registration">
	<form:form method="post" enctype="multipart/form-data"
		modelAttribute="registrationForm" class="form-horizontal">

		<!-- Global errors -->
		<c:if test="${validationErrors != null}">
			<div class="errors">
				<ul>
					<c:forEach items="${validationErrors}" var="error">
						<li><c:out value="${error.message}" /></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<div class="control-group">
			<form:label path="subject" cssClass="control-label">Subject</form:label>
			<div class="controls">
				<form:input path="subject" cssClass="input-xlarge" />
				<form:errors path="subject" cssClass="errors" />
			</div>
		</div>
		<div class="control-group">
			<form:label path="body" cssClass="control-label">Body</form:label>
			<div class="controls">
				<form:textarea path="body" rows="5" cols="30"
					cssClass="input-xlarge" />
				<form:errors path="body" cssClass="errors" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><b>Attachments</b></label>
			<div class="controls">
				<input type="file" name="attachments" multiple="multiple"
					class="input-file" />
				<form:errors path="attachments" cssClass="errors" />	
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<input type="submit"
			value="<spring:message code="button.registration.submit" />" />
			</div>
		</div>
	</form:form>
</template:basic>
