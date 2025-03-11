package com.fptaptech.demo2.Service;

import com.fptaptech.demo2.DAO.UserDAO;
import com.fptaptech.demo2.model.User;
import com.fptaptech.demo2.utils.JWTUtils;

import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    // ✅ Trả về User thay vì Optional<String>
    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);

        // ✅ So sánh password (sau này thay bằng hash)
        if (user != null && user.getPassword().equals(password)) {
            return user; // ✅ Trả về User nếu hợp lệ
        }
        return null; // ❌ Nếu sai, trả về null
    }

    // ✅ Hàm này tạo JWT sau khi user đăng nhập thành công
    public String generateJWT(User user) {
        return JWTUtils.generateToken(user.getUsername(), user.getRole());
    }
}
