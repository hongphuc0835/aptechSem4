package org.example.sis.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sis.dao.StudentDAO;
import org.example.sis.model.Student;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Student student = studentDAO.getStudentById(id);
                request.setAttribute("student", student);
                request.getRequestDispatcher("edit-student.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        } else if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                studentDAO.deleteStudent(id);
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
                Student student = new Student();
                student.setStudentCode(request.getParameter("studentCode"));
                student.setFullName(request.getParameter("fullName"));
                student.setAddress(request.getParameter("address"));

                studentDAO.addStudent(student);
                response.sendRedirect("index.jsp");
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        } else if ("update".equals(action)) {
            try {
                Student student = new Student();
                student.setId(Integer.parseInt(request.getParameter("id")));
                student.setStudentCode(request.getParameter("studentCode"));
                student.setFullName(request.getParameter("fullName"));
                student.setAddress(request.getParameter("address"));

                studentDAO.updateStudent(student);
                response.sendRedirect("index.jsp");
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
    }
}