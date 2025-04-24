<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Student</title>
    <link rel="stylesheet" href="css/style3.css">
</head>
<body>
<h1>Add New Student</h1>
<form action="student" method="post">
    <input type="hidden" name="action" value="add">
    <div>
        <label>Student Code:</label>
        <input type="text" name="studentCode" required>
    </div>
    <div>
        <label>Full Name:</label>
        <input type="text" name="fullName" required>
    </div>
    <div>
        <label>Address:</label>
        <input type="text" name="address">
    </div>
    <div>
        <input type="submit" value="Add Student" class="btn">
        <a href="index.jsp" class="btn">Cancel</a>
    </div>
</form>
</body>
</html>
