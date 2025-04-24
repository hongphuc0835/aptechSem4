package org.example.sis.dao;


import org.example.sis.model.Score;
import org.example.sis.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    public void addScore(Score score) throws SQLException {
        String sql = "INSERT INTO student_score_t (student_id, subject_id, score1, score2) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, score.getStudentId());
            stmt.setInt(2, score.getSubjectId());
            stmt.setDouble(3, score.getScore1());
            stmt.setDouble(4, score.getScore2());
            stmt.executeUpdate();
        }
    }

    public List<Score> getScoresByStudent(int studentId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT * FROM student_score_t WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Score score = new Score();
                    score.setId(rs.getInt("student_score_id"));
                    score.setStudentId(rs.getInt("student_id"));
                    score.setSubjectId(rs.getInt("subject_id"));
                    score.setScore1(rs.getDouble("score1"));
                    score.setScore2(rs.getDouble("score2"));
                    scores.add(score);
                }
            }
        }
        return scores;
    }

    // Thêm phương thức lấy điểm số theo ID
    public Score getScoreById(int id) throws SQLException {
        String sql = "SELECT * FROM student_score_t WHERE student_score_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Score score = new Score();
                    score.setId(rs.getInt("student_score_id"));
                    score.setStudentId(rs.getInt("student_id"));
                    score.setSubjectId(rs.getInt("subject_id"));
                    score.setScore1(rs.getDouble("score1"));
                    score.setScore2(rs.getDouble("score2"));
                    return score;
                }
            }
        }
        return null;
    }

    // Thêm phương thức cập nhật điểm số
    public void updateScore(Score score) throws SQLException {
        String sql = "UPDATE student_score_t SET score1 = ?, score2 = ? WHERE student_score_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, score.getScore1());
            stmt.setDouble(2, score.getScore2());
            stmt.setInt(3, score.getId());
            stmt.executeUpdate();
        }
    }

    // Thêm phương thức xóa điểm số
    public void deleteScore(int id) throws SQLException {
        String sql = "DELETE FROM student_score_t WHERE student_score_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}