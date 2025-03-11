package com.fptaptech.demo2.DAO;

import com.fptaptech.demo2.model.User;
import com.fptaptech.demo2.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"), // ✅ Nếu mật khẩu đã hash, sẽ lấy giá trị này
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ❌ Nếu không tìm thấy user, trả về null
    }

}
