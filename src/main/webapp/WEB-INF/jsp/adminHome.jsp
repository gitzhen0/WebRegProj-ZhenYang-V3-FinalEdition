<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <style>
        <%@include file="/WEB-INF/static/css/styles.css" %>
    </style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Administrator Home Page</h2>
<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/all">Course</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/application/all">Applications</a></li>
    </ul>
</nav>

<h3>Student Table</h3>

<table>
    <thead>
    <tr>
        <th>Student</th>
        <th>Email</th>
        <th>Department</th>
        <th>School</th>
        <th>Status</th>
        <th>Setting</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${adminHomeDisplays}" var="adminHomeDisplays">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/admin/student/${adminHomeDisplays.student_id}">${adminHomeDisplays.first_name} ${adminHomeDisplays.last_name}</a>
            </td>
            <td>${adminHomeDisplays.email}</td>
            <td>${adminHomeDisplays.department_name}</td>
            <td>${adminHomeDisplays.school_name}</td>
            <td>${adminHomeDisplays.is_active}</td>
            <c:choose>
                <c:when test='${adminHomeDisplays.is_active == "Active"}'>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/student/${adminHomeDisplays.student_id}/${adminHomeDisplays.is_active}" method="post">
                            <input type="hidden" name="_method" value="patch" />
                            <button type="submit">Deactivate</button>
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/student/${adminHomeDisplays.student_id}/${adminHomeDisplays.is_active}" method="post">
                            <input type="hidden" name="_method" value="patch" />
                            <button type="submit">Activate</button>
                        </form>
                    </td>
                </c:otherwise>
            </c:choose>

        </tr>
    </c:forEach>
    </tbody>
</table>

<div>

    <a href="${pageContext.request.contextPath}/admin/home?page=1&limit=${limit}">First</a>

    <c:if test="${page > 1}">
        <a href="${pageContext.request.contextPath}/admin/home?page=${page - 1}&limit=${limit}">Previous</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="${pageContext.request.contextPath}/admin/home?page=${i}&limit=${limit}">${i}</a>
    </c:forEach>

    <c:if test="${page < totalPages}">
        <a href="${pageContext.request.contextPath}/admin/home?page=${page + 1}&limit=${limit}">Next</a>
    </c:if>

    <a href="${pageContext.request.contextPath}/admin/home?page=${totalPages}&limit=${limit}">Last</a>

</div>

</body>

</html>