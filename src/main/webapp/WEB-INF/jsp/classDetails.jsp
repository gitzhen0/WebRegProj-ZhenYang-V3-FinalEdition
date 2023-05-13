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
<h2>Class Details Page</h2>

<nav>
    <ul class="horizontal-menu">
        <c:choose>
            <c:when test="${userSessionVO.is_admin == '1'}">
                <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/course/all">Course</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/class/all">Classes</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/application/all">Applications</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
                <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/class/all">Classes</a></li>
                <li><a href="${pageContext.request.contextPath}/application/all">Applications</a></li>
            </c:otherwise>
        </c:choose>

    </ul>
</nav>
<br>

<h3>Class - Course Details</h3>
<table>
    <thead>
    <tr>
        <th>Course</th>
        <th>Course Code</th>
        <th>Department</th>
        <th>School</th>
        <th>Description</th>
        <th>Enrollment/Capacity</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${webRegClassDisplay.course_name}</td>
        <td>${webRegClassDisplay.course_code}</td>
        <td>${webRegClassDisplay.department_name}</td>
        <td>${webRegClassDisplay.school_name}</td>
        <td>${webRegClassDisplay.course_description}</td>
        <td>${webRegClassDisplay.enrollment_num}/${webRegClassDisplay.capacity}</td>
        <td>${webRegClassDisplay.is_active}</td>
    </tr>
    </tbody>
</table>

<br>
<h3>Class - Semester Details</h3>
<table>
    <thead>
    <tr>
        <th>Semester</th>
        <th>Start Date</th>
        <th>End Date</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${classToSemesterDisplay.semester_name}</td>
        <td>${classToSemesterDisplay.start_date}</td>
        <td>${classToSemesterDisplay.end_date}</td>
    </tr>
    </tbody>
</table>

<br>
<h3>Class - Lecture Details</h3>
<table>
    <thead>
    <tr>
        <th>Day of Week</th>
        <th>Start Time</th>
        <th>End Time</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${classToLectureDisplay.day_of_the_week}</td>
        <td>${classToLectureDisplay.start_time}</td>
        <td>${classToLectureDisplay.end_time}</td>
    </tr>
    </tbody>
</table>

<br>
<h3>Class - Professor Details</h3>
<table>
    <thead>
    <tr>
        <th>Professor</th>
        <th>Email</th>
        <th>Department</th>
        <th>School</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${classToProfessorDisplay.first_name} ${classToProfessorDisplay.last_name}</td>
        <td>${classToProfessorDisplay.email}</td>
        <td>${classToProfessorDisplay.department_name}</td>
        <td>${classToProfessorDisplay.school_name}</td>
    </tr>
    </tbody>
</table>

<br>
<h3>Class - Classroom Details</h3>
<table>
    <thead>
    <tr>
        <th>Classroom</th>
        <th>Building</th>
        <th>Capacity</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${classToClassroomDisplay.name}</td>
        <td>${classToClassroomDisplay.building}</td>
        <td>${classToClassroomDisplay.capacity}</td>
    </tr>
    </tbody>
</table>

<br>
<h3>Class - Prerequisite Details</h3>
<table>
    <thead>
    <tr>
        <th>Prerequisite</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/class/${classToPrerequisiteDisplay.prerequisite_id}">${classToPrerequisiteDisplay.prerequisite_name}</a>
        </td>
    </tr>
    </tbody>
</table>

<br>

<c:if test="${userSessionVO.is_admin.equals('1')}">
    <h3>Class - Enrolled Student Details</h3>
    <h6>Only Shows Ongoing Students</h6>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Department</th>
        <th>School</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${studentClassDisplays}" var="studentClassDisplays">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/admin/student/${studentClassDisplays.student_id}">${studentClassDisplays.first_name} ${studentClassDisplays.last_name}</a>
            </td>
            <td>${studentClassDisplays.email}</td>
            <td>${studentClassDisplays.department_name}</td>
            <td>${studentClassDisplays.school_name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>
</body>

</html>