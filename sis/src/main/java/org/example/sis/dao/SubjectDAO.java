package org.example.sis.dao;


import org.example.sis.model.Subject;
import org.example.sis.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subject_t";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubjectCode(rs.getString("subject_code"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setCredit(rs.getInt("credit"));
                subjects.add(subject);
            }
        }
        return subjects;
    }

}