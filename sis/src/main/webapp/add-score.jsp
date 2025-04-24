<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.sis.dao.StudentDAO" %>
<%@ page import="org.example.sis.model.Student" %>
<%@ page import="org.example.sis.dao.SubjectDAO" %>
<%@ page import="org.example.sis.model.Subject" %>
<html>
<head>
    <title>Add Score</title>
    <link rel="stylesheet" href="css/style2.css">
</head>
<body>
<h1>Add Score</h1>
<form action="score" method="post">
    <input type="hidden" name="action" value="add">
    <div>
        <label>Student:</label>
        <select name="studentId" required>
            <%
                StudentDAO studentDAO = new StudentDAO();
                List<Student> students = studentDAO.getAllStudents();
                for (Student student : students) {
            %>
            <option value="<%= student.getId() %>"><%= student.getFullName() %></option>
            <% } %>
        </select>
    </div>
    <div>
        <label>Subject:</label>
        <select name="subjectId" required>
            <%
                SubjectDAO subjectDAO = new SubjectDAO();
                List<Subject> subjects = subjectDAO.getAllSubjects();
                for (Subject subject : subjects) {
            %>
            <option value="<%= subject.getId() %>"><%= subject.getSubjectName() %></option>
            <% } %>
        </select>
    </div>
    <div>
        <label>Score 1:</label>
        <input type="number" step="0.1" min="0" max="10" name="score1" required>
    </div>
    <div>
        <label>Score 2:</label>
        <input type="number" step="0.1" min="0" max="10" name="score2" required>
    </div>
    <div>
        <input type="submit" value="Add Score" class="btn">
        <a href="index.jsp" class="btn">Cancel</a>
    </div>
</form>
</body>
</html>
