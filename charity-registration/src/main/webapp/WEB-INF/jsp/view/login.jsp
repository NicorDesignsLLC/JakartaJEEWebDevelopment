<template:loggedOut htmlTitle="Log In" bodyTitle="Log In">
        You must log in to access the charity registration site.<br /><br />
        <c:if test="${loginFailed}">
            <b>The user name and password you entered are not correct. Please try
                again.</b><br /><br />
        </c:if>
        <form method="POST" action="<c:url value="/login" />">
            User name<br />
            <input type="text" name="username" /><br /><br />
            Password<br />
            <input type="password" name="password" /><br /><br />
            <input type="submit" value="Log In" />
        </form>
</template:loggedOut>
