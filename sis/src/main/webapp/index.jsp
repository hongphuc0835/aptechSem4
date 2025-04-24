<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.sis.dao.StudentDAO" %>
<%@ page import="org.example.sis.dao.SubjectDAO" %>
<%@ page import="org.example.sis.dao.ScoreDAO" %>
<%@ page import="org.example.sis.model.Student" %>
<%@ page import="org.example.sis.model.Score" %>
<%@ page import="org.example.sis.model.Subject" %>
<%@ page import="org.example.sis.util.GradeCalculator" %>
<html>
<head>
    <title>Student Information System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Student Information</h1>

<a href="add-student.jsp" class="btn">+ Add Student</a>
<a href="add-score.jsp" class="btn">+ Add Score</a>

<%
    StudentDAO studentDAO = new StudentDAO();
    SubjectDAO subjectDAO = new SubjectDAO();
    ScoreDAO scoreDAO = new ScoreDAO();
    List<Student> students = studentDAO.getAllStudents();
    int idCounter = 1;
%>

<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Student ID</th>
        <th>Full Name</th>
        <th>Subject</th>
        <th>Score 1</th>
        <th>Score 2</th>
        <th>Credit</th>
        <th>Grade</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Student student : students) {
            List<Score> scores = scoreDAO.getScoresByStudent(student.getId());

            if (scores.isEmpty()) {
    %>
    <tr>
        <td><%= idCounter++ %></td>
        <td><%= student.getStudentCode() %></td>
        <td><%= student.getFullName() %></td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
        <td>
            <div class="action-container">
                <div class="dropdown">
                    <span class="action-icon">✏️</span>
                    <div class="dropdown-content">
                        <a href="student?action=edit&id=<%= student.getId() %>">Edit Student</a>
                    </div>
                </div>
            </div>
        </td>
    </tr>
    <%
    } else {
        for (Score score : scores) {
            Subject subject = subjectDAO.getAllSubjects().stream()
                    .filter(s -> s.getId() == score.getSubjectId())
                    .findFirst().orElse(null);
            double finalScore = GradeCalculator.calculateFinalScore(score.getScore1(), score.getScore2());
            String grade = GradeCalculator.calculateGrade(finalScore);
    %>
    <tr>
        <td><%= idCounter++ %></td>
        <td><%= student.getStudentCode() %></td>
        <td><%= student.getFullName() %></td>
        <td><%= subject != null ? subject.getSubjectName() : "-" %></td>
        <td><%= String.format("%.1f", score.getScore1()) %></td>
        <td><%= String.format("%.1f", score.getScore2()) %></td>
        <td><%= subject != null ? subject.getCredit() : "-" %></td>
        <td><%= grade %></td>
        <td>
            <div class="action-container">
                <div class="dropdown">
                    <span class="action-icon">✏️</span>
                    <div class="dropdown-content">
                        <a href="student?action=edit&id=<%= student.getId() %>">Edit Student</a>
                        <a href="score?action=edit&id=<%= score.getId() %>">Edit Score</a>
                    </div>
                </div>
            </div>
        </td>
    </tr>
    <%
                }
            }
        }
    %>
    </tbody>
</table>

<script>
    // JS for dropdown toggle
    document.addEventListener("click", function (event) {
        let dropdown = event.target.closest(".dropdown");
        if (dropdown) {
            let dropdownContent = dropdown.querySelector(".dropdown-content");
            document.querySelectorAll(".dropdown-content").forEach(content => {
                if (content !== dropdownContent) content.style.display = "none";
            });
            if (event.target.classList.contains("action-icon")) {
                dropdownContent.style.display = dropdownContent.style.display === "block" ? "none" : "block";
            }
        } else {
            document.querySelectorAll(".dropdown-content").forEach(content => content.style.display = "none");
        }
    });
</script>

</body>
</html>
