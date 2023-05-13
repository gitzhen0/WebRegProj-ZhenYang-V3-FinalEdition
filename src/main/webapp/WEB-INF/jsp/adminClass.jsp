<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Class</title>
    <style>
        <%@include file="/WEB-INF/static/css/styles.css" %>
    </style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Administrator Class Page</h2>

<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/course/all">Course</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/application/all">Applications</a></li>
    </ul>
</nav>

<h3> Class Table</h3>
<table>
    <thead>
    <tr>
        <th>Course</th>
        <th>Course Code</th>
        <th>Department</th>
        <th>School</th>
        <th>Semester</th>
        <th>Enrollment/Capacity</th>
        <th>Status</th>
        <th>Setting</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${adminClassDisplayList}" var="adminClassDisplayList">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/class/${adminClassDisplayList.class_id}"> ${adminClassDisplayList.course_name}</a>
            </td>
            <td>${adminClassDisplayList.course_code}</td>
            <td>${adminClassDisplayList.department_name}</td>
            <td>${adminClassDisplayList.school_name}</td>
            <td>${adminClassDisplayList.semester_name}</td>
            <td>${adminClassDisplayList.enrollment_num}/${adminClassDisplayList.capacity}</td>
            <td>${adminClassDisplayList.is_active}</td>
            <c:choose>
                <c:when test='${adminClassDisplayList.is_active == "Active"}'>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/class/${adminClassDisplayList.class_id}/${adminClassDisplayList.is_active}" method="post">
                            <input type="hidden" name="_method" value="patch" />
                            <button type="submit">Deactivate</button>
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/class/${adminClassDisplayList.class_id}/${adminClassDisplayList.is_active}" method="post">
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

</body>

</html>