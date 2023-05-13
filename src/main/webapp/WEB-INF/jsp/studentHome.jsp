<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <style><%@include file="/WEB-INF/static/css/styles.css"%></style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Student Home Page</h2>
<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/application/all">Applications</a></li>
    </ul>
</nav>


<h3>My Course Table</h3>

<table>
    <thead>
    <tr>
        <th>Course Name</th>
        <th>Course Code</th>
        <th>Department Name</th>
        <th>School Name</th>
        <th>Semester Name</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${studentClasses}" var="studentClass">
        <tr>
            <td><a href="${pageContext.request.contextPath}/class/${studentClass.class_id}">${studentClass.course_name}</a></td>
            <td>${studentClass.course_code}</td>
            <td>${studentClass.department_name}</td>
            <td>${studentClass.school_name}</td>
            <td>${studentClass.semester_name}</td>
            <td>${studentClass.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div>

    <a href="${pageContext.request.contextPath}/home?page=1&limit=${limit}">First</a>

    <c:if test="${page > 1}">
        <a href="${pageContext.request.contextPath}/home?page=${page - 1}&limit=${limit}">Previous</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="${pageContext.request.contextPath}/home?page=${i}&limit=${limit}">${i}</a>
    </c:forEach>

    <c:if test="${page < totalPages}">
        <a href="${pageContext.request.contextPath}/home?page=${page + 1}&limit=${limit}">Next</a>
    </c:if>

    <a href="${pageContext.request.contextPath}/home?page=${totalPages}&limit=${limit}">Last</a>

</div>

</body>

</html>