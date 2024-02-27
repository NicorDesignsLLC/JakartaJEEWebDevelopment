<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="com.nicordesigns.site.LoginController.Form"--%>
<template:loggedOut htmlTitle="Log In" bodyTitle="Log In">
        You must log in to access the charity registration site.<br /><br />
        <c:if test="${loginFailed}">
            <b>The user name and password you entered are not correct. Please try
                again.</b><br /><br />
        </c:if>
        <form:form method="post" modelAttribute="loginForm">
        <form:label path="username">User Name</form:label><br />
        <form:input path="username" /><br /><br />
        <form:label path="password">Password</form:label><br />
        <form:password path="password" /><br /><br />
        <input type="submit" value="Log In" />
    </form:form>
</template:loggedOut>
