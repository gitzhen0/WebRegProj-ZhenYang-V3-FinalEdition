<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${webRegClassDisplay.course_name}</title>
    <style>
        <%@include file="/WEB-INF/static/css/styles.css" %>
    </style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Administrator Student Info Page</h2>
<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/all">Course</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/application/all">Applications</a></li>
    </ul>
</nav>

<h3>Student Details Table</h3>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
        <th>School</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${adminHomeDisplay.first_name} ${adminHomeDisplay.last_name}</td>
        <td>${adminHomeDisplay.email}</td>
        <td>${adminHomeDisplay.department_name}</td>
        <td>${adminHomeDisplay.school_name}</td>
        <td>${adminHomeDisplay.is_active}</td>
    </tr>
    </tbody>
</table>

<br>
<h3>Student's Course Table</h3>
<table>
    <thead>
    <tr>
        <th>Course</th>
        <th>Course Code</th>
        <th>Department</th>
        <th>School</th>
        <th>Semester</th>
        <th>Status</th>
        <th>Set</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${studentClassDisplayList}" var="studentClassDisplayList">
        <tr>
            <td><a href="${pageContext.request.contextPath}/class/${studentClassDisplayList.class_id}">${studentClassDisplayList.course_name}</a></td>
            <td>${studentClassDisplayList.course_code}</td>
            <td>${studentClassDisplayList.department_name}</td>
            <td>${studentClassDisplayList.school_name}</td>
            <td>${studentClassDisplayList.semester_name}</td>
            <td>${studentClassDisplayList.status}</td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/student/${adminHomeDisplay.student_id}/class/${studentClassDisplayList.class_id}" method="post">
                    <input type="hidden" name="_method" value="patch" />
                    <select name="status">
                        <option value="pass">Pass</option>
                        <option value="fail">Fail</option>
                    </select>
                    <br>
                    <button type="submit">Submit</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>

</html>