package org.example.sis.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sis.dao.ScoreDAO;
import org.example.sis.model.Score;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/score")
public class ScoreServlet extends HttpServlet {
    private ScoreDAO scoreDAO = new ScoreDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Score score = scoreDAO.getScoreById(id);
                request.setAttribute("score", score);
                request.getRequestDispatcher("edit-score.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        } else if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                scoreDAO.deleteScore(id);
                response.sendRedirect("index.jsp");
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            try {
                Score score = new Score();
                score.setStudentId(Integer.parseInt(request.getParameter("studentId")));
                score.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
                score.setScore1(Double.parseDouble(request.getParameter("score1")));
                score.setScore2(Double.parseDouble(request.getParameter("score2")));

                scoreDAO.addScore(score);
                response.sendRedirect("index.jsp");
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        } else if ("update".equals(action)) {
            try {
                Score score = new Score();
                score.setId(Integer.parseInt(request.getParameter("id")));
                score.setStudentId(Integer.parseInt(request.getParameter("studentId")));
                score.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
                score.setScore1(Double.parseDouble(request.getParameter("score1")));
                score.setScore2(Double.parseDouble(request.getParameter("score2")));

                scoreDAO.updateScore(score);
                response.sendRedirect("index.jsp");
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
    }
}