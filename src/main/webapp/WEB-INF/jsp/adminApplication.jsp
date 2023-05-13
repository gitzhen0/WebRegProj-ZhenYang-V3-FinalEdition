<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Application</title>
    <style>
        <%@include file="/WEB-INF/static/css/styles.css" %>
    </style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Administrator Application Page</h2>

<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/all">Course</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/application/all">Applications</a></li>
    </ul>
</nav>


<h3>Pending Application Table</h3>
<table>
    <thead>
    <tr>
        <th>Student</th>
        <th>Student Email</th>
        <th>Course</th>
        <th>Semester</th>
        <th>Creation Time</th>
        <th>Request</th>
        <th>Status</th>
        <th>Feedback</th>
        <th>Setting</th>


    </tr>
    </thead>
    <tbody>
    <c:forEach items="${adminApplicationList}" var="adminApplicationList">
        <form action="${pageContext.request.contextPath}/admin/application/${adminApplicationList.application_id}" method="post">
            <input type="hidden" name="_method" value="patch"/>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/admin/student/${adminApplicationList.student_id}">${adminApplicationList.first_name} ${adminApplicationList.last_name}</a>
            </td>
            <td>${adminApplicationList.email}</td>
            <td>
                <a href="${pageContext.request.contextPath}/class/${adminApplicationList.class_id}"> ${adminApplicationList.course_name}</a>
            </td>
            <td>${adminApplicationList.semester_name}</td>
            <td>${adminApplicationList.creation_time}</td>
            <td>${adminApplicationList.request}</td>
            <td>${adminApplicationList.status}</td>
            <td>
                <input type="text" name="feedback">
            </td>
            <td>
                <select name="status">
                    <option value="approved">Approve</option>
                    <option value="rejected">Reject</option>
                </select>
            </td>
<%--                <input type="text" name="description" placeholder="Description (optional)">--%>
            <td>
                <button type="submit">Submit</button>
            </td>

        </tr>
        </form>
    </c:forEach>
    </tbody>
</table>

</body>

</html>