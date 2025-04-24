<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Student</title>
    <link rel="stylesheet" href="css/style4.css">
</head>
<body>
<h1>Edit Student</h1>
<form action="student" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${student.id}">
    <div>
        <label>Student Code:</label>
        <input type="text" name="studentCode" value="${student.studentCode}" required>
    </div>
    <div>
        <label>Full Name:</label>
        <input type="text" name="fullName" value="${student.fullName}" required>
    </div>
    <div>
        <label>Address:</label>
        <input type="text" name="address" value="${student.address}">
    </div>
    <div>
        <input type="submit" value="Update" class="btn">
        <a href="index.jsp" class="btn">Cancel</a>
    </div>
</form>
</body>
</html>
