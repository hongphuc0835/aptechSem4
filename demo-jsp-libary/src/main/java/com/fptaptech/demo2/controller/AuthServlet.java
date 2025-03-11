package com.fptaptech.demo2.controller;

import com.fptaptech.demo2.Service.AuthService;
import com.fptaptech.demo2.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // ✅ Sử dụng User thay vì Optional<String>
        User user = authService.login(username, password);

        if (user != null) {
            // ✅ Lưu session
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("userId", user.getId());

            // ✅ Set cookie JWT nếu cần
            Cookie jwtCookie = new Cookie("jwt_token", authService.generateJWT(user));
            jwtCookie.setMaxAge(10 * 60); // 10 phút
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            // ✅ Điều hướng dựa trên ROLE, không phải username
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/books.jsp");
            }
        } else {
            // ❌ Đăng nhập thất bại
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
