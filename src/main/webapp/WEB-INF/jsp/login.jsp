<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
    <style><%@include file="/WEB-INF/static/css/styles.css"%></style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h3>Account Login Page</h3>

<br>
<a href="${pageContext.request.contextPath}/signup">Sign Up instead</a>
<br>

<form action="/login" method="post">

    <p>
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>
    </p>

    <p>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
    </p>
    <input type="submit" value="Log in">
</form>

</body>
</html>