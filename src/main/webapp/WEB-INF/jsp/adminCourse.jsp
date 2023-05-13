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
<h2>Administrator Course Page</h2>

<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/all">Course</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/application/all">Applications</a></li>
    </ul>
</nav>

<h3>Course Table</h3>
<table>
    <thead>
    <tr>
        <th>Course</th>
        <th>Course Code</th>
        <th>Department</th>
        <th>School</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${adminCourseDisplayList}" var="adminCourseDisplayList">
        <tr>
            <td>${adminCourseDisplayList.course_name}</td>
            <td>${adminCourseDisplayList.course_code}</td>
            <td>${adminCourseDisplayList.department_name}</td>
            <td>${adminCourseDisplayList.school_name}</td>
            <td>${adminCourseDisplayList.description}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<hr>
<h3> Add a course here</h3>

<form action="/admin/course" method="post">

    <p>
        <label for="course_name">Course Name:</label>
        <input type="text" name="course_name" id="course_name" required>
    </p>

    <p>
        <label for="course_code">Course Code:</label>
        <input type="text" name="course_code" id="course_code" required>
    </p>

    <p>
        <label for="department_id">Department: </label>
        <select name="department_id" id="department_id" required>
            <c:forEach items="${departmentList}" var="department">
                <option value="${department.id}">${department.name}</option>
            </c:forEach>
        </select>
    </p>

    <p>
        <label for="description">Description: </label>
        <input type="text" name="description" id="description" required>
    </p>


    <input type="submit" value="Submit">
</form>

<hr>
<h3> Add a class here</h3>

<form action="/admin/class" method="post">

    <p>
        <label for="course_id">Course: </label>
        <select name="course_id" id="course_id" required>
            <c:forEach items="${courseList}" var="course">
                <option value="${course.course_id}">${course.course_name}</option>
            </c:forEach>
        </select>
    </p>

    <p>
        <label for="professor_id">Professor: </label>
        <select name="professor_id" id="professor_id" required>
            <c:forEach items="${professorList}" var="professor">
                <option value="${professor.id}">${professor.first_name} ${professor.last_name}</option>
            </c:forEach>
        </select>
    </p>

    <p>
        <label for="semester_id">Semester: </label>
        <select name="semester_id" id="semester_id" required>
            <c:forEach items="${semesterList}" var="semester">
                <option value="${semester.id}">${semester.name}</option>
            </c:forEach>
        </select>
    </p>

    <p>
        <label for="classroom_id">Classroom: </label>
        <select name="classroom_id" id="classroom_id" required>
            <c:forEach items="${classroomList}" var="classroom">
                <option value="${classroom.id}">${classroom.name}</option>
            </c:forEach>
        </select>
    </p>

    <p>
        <label for="capacity">Capacity:</label>
        <input type="number" name="capacity" id="capacity" required>
    </p>

    <p>

    <div>
        <label>Lecture:</label>
        <label for="dayOfWeek">Day of Week:</label>
        <select id="dayOfWeek" name="dayOfWeek">
            <option value="1">Monday</option>
            <option value="2">Tuesday</option>
            <option value="3">Wednesday</option>
            <option value="4">Thursday</option>
            <option value="5">Friday</option>
            <option value="6">Saturday</option>
            <option value="7">Sunday</option>
        </select>
        <label for="startTime">Start Time:</label>
        <input type="time" id="startTime" name="startTime" required>
        <label for="endTime">End Time:</label>
        <input type="time" id="endTime" name="endTime" required>
    </div>
    </p>
    <input type="submit" value="Submit">
</form>


</body>

</html>