<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <style><%@include file="/WEB-INF/static/css/styles.css"%></style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h3>Account Sign Up Page</h3>

<br>
<a href="${pageContext.request.contextPath}/login">Log in instead</a>
<br>

<form action="/signup" method="post">

    <p>
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>
    </p>

    <p>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
    </p>

    <p>
        <label for="first_name">First Name: </label>
        <input type="text" name="first_name" id="first_name" required>
    </p>

    <p>
        <label for="last_name">Last Name: </label>
        <input type="text" name="last_name" id="last_name" required>
    </p>

    <p>
        <label for="department_id">Department: </label>
        <select name="department_id" id="department_id" required>
            <c:forEach items="${departments}" var="department">
                <option value="${department.id}">${department.name}</option>
            </c:forEach>
        </select>
    </p>


    <input type="submit" value="Sign up">
</form>

<%--<c:if test="${not empty errorMessage}">--%>
<%--    <p style="color: red;">${errorMessage}</p>--%>
<%--</c:if>--%>
</body>
</html>