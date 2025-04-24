package org.example.sis.dao;




import org.example.sis.model.Student;
import org.example.sis.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student_t (student_code, full_name, address) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentCode());
            stmt.setString(2, student.getFullName());
            stmt.setString(3, student.getAddress());
            stmt.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student_t";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setStudentCode(rs.getString("student_code"));
                student.setFullName(rs.getString("full_name"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            }
        }
        return students;
    }

    // Thêm phương thức lấy sinh viên theo ID
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM student_t WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("student_id"));
                    student.setStudentCode(rs.getString("student_code"));
                    student.setFullName(rs.getString("full_name"));
                    student.setAddress(rs.getString("address"));
                    return student;
                }
            }
        }
        return null;
    }

    // Thêm phương thức cập nhật sinh viên
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student_t SET student_code = ?, full_name = ?, address = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentCode());
            stmt.setString(2, student.getFullName());
            stmt.setString(3, student.getAddress());
            stmt.setInt(4, student.getId());
            stmt.executeUpdate();
        }
    }

    // Thêm phương thức xóa sinh viên
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM student_t WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}