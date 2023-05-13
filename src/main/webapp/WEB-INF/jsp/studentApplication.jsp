<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Application</title>
    <style><%@include file="/WEB-INF/static/css/styles.css"%></style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Student Application Page</h2>
<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/application/all">Applications</a></li>
    </ul>
</nav>


<h3>My Application Table</h3>
<table>
    <thead>
    <tr>
        <th>Course</th>
        <th>Course Code</th>
        <th>Semester</th>
        <th>Creation Time</th>
        <th>Request</th>
        <th>Status</th>
        <th>Feedback</th>
        <th>Setting</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${studentApplicationDisplays}" var="studentApplicationDisplays">
        <tr>
            <td>${studentApplicationDisplays.course_name}</td>
            <td>${studentApplicationDisplays.course_code}</td>
            <td>${studentApplicationDisplays.semester_name}</td>
            <td>${studentApplicationDisplays.creation_time}</td>
            <td>${studentApplicationDisplays.request}</td>
            <td>${studentApplicationDisplays.status}</td>
            <td>${studentApplicationDisplays.feedback}</td>
            <c:choose>
                <c:when test='${studentApplicationDisplays.status == "pending"}'>
                    <td>
                        <form action="${pageContext.request.contextPath}/application/${studentApplicationDisplays.application_id}" method="post">
                            <input type="hidden" name="_method" value="delete" />
                            <button type="submit">Cancel</button>
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>


</body>

</html>