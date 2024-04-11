<%--@elvariable id="registrationForm" type="com.nicordesigns.site.RegistrationController.Form"--%>
<%--@elvariable id="registration" type="com.nicordesigns.site.Registration"--%>
<template:basic htmlTitle="Create a Registration" bodyTitle="Create a Registration">
    <form:form method="post" enctype="multipart/form-data"
               modelAttribute="registrationForm">
        <form:label path="subject">Subject</form:label><br />
        <form:input path="subject"/><br />
        <form:label path="body">Body</form:label><br />
        <form:textarea path="body" rows="5" cols="30" /><br />
        <b>Attachments</b><br />
        <input type="file" name="attachments" multiple="multiple"/><br />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>
