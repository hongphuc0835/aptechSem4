<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Edit Score</title>
    <link rel="stylesheet" href="css/style4.css">
</head>
<body>
<h1>Edit Score</h1>
<form action="score" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${score.id}">
    <input type="hidden" name="studentId" value="${score.studentId}">
    <input type="hidden" name="subjectId" value="${score.subjectId}">
    <div>
        <label>Score 1:</label>
        <input type="number" step="0.1" min="0" max="10" name="score1" value="${score.score1}" required>
    </div>
    <div>
        <label>Score 2:</label>
        <input type="number" step="0.1" min="0" max="10" name="score2" value="${score.score2}" required>
    </div>
    <div>
        <input type="submit" value="Update" class="btn">
        <a href="index.jsp" class="btn">Cancel</a>
    </div>
</form>
</body>
</html>
