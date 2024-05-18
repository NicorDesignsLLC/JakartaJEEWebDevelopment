<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page isELIgnored="false" %>

<%-- @elvariable id="registrationForm" type="com.nicordesigns.site.RegistrationController.Form" --%>
<%-- @elvariable id="registration" type="com.nicordesigns.site.Registration" --%>

<template:basic htmlTitle="Create a Registration" bodyTitle="Create a Registration">
    <form:form method="post" enctype="multipart/form-data" modelAttribute="registrationForm" class="form-horizontal">
        <div class="control-group">
            <form:label path="subject" cssClass="control-label">Subject</form:label>
            <div class="controls">
                <form:input path="subject" cssClass="input-xlarge"/>
                <form:errors path="subject" cssClass="help-inline text-error"/>
            </div>
        </div>
        <div class="control-group">
            <form:label path="body" cssClass="control-label">Body</form:label>
            <div class="controls">
                <form:textarea path="body" rows="5" cols="30" cssClass="input-xlarge"/>
                <form:errors path="body" cssClass="help-inline text-error"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><b>Attachments</b></label>
            <div class="controls">
                <input type="file" name="attachments" multiple="multiple" class="input-file"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input type="submit" value="Submit" class="btn btn-primary"/>
            </div>
        </div>
    </form:form>
</template:basic>
