<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Class</title>
    <style><%@include file="/WEB-INF/static/css/styles.css"%></style>
</head>
<body>
<h1>Welcome to Course Management System</h1>
<h2>Student Class Management page</h2>
<nav>
    <ul class="horizontal-menu">
        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/class/all">Classes</a></li>
        <li><a href="${pageContext.request.contextPath}/application/all">Applications</a></li>
    </ul>
</nav>


<h3>Class Table</h3>
<table>
    <thead>
    <tr>
        <th>Course</th>
        <th>Course Code</th>
        <th>Department</th>
        <th>School</th>
        <th>Semester</th>
        <th>Enrollment Number/Capacity</th>
        <th>Enrollment Status</th>
        <th>Setting</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${classManagementDisplays}" var="classManagementDisplays">
        <tr>
            <td><a href="${pageContext.request.contextPath}/class/${classManagementDisplays.class_id}">${classManagementDisplays.course_name}</a></td>
            <td>${classManagementDisplays.course_code}</td>
            <td>${classManagementDisplays.department_name}</td>
            <td>${classManagementDisplays.school_name}</td>
            <td>${classManagementDisplays.semester_name}</td>
            <td>${classManagementDisplays.enrollment_num}/${classManagementDisplays.capacity}</td>
            <td>${classManagementDisplays.isEnrolled}</td>


            <td>
                <form action="${pageContext.request.contextPath}/class/${classManagementDisplays.class_id}" method="post">
                    <input type="hidden" name="class_id" value="${classManagementDisplays.class_id}" />
                    <c:choose>
                        <c:when test="${classManagementDisplays.isEnrolled == 'Enrolled'}">
                            <button type="submit" name="action" value="withdraw">Withdraw</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" name="action" value="add">Add</button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </td>


<%--            <c:choose>--%>
<%--                <c:when test='${classManagementDisplays.isEnrolled == "Enrolled"}'>--%>
<%--                    <td><button>Withdraw</button></td>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <td><button>Add</button></td>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>


        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>Setting Operation Feedbacks</h3>
<p>${errorMessage}</p>

</body>

</html>