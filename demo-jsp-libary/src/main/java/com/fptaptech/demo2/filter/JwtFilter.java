package com.fptaptech.demo2.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fptaptech.demo2.utils.JWTUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
@WebFilter(
        filterName = "JwtFilter",
        urlPatterns = {"/user.jsp", "/admin.jsp"}
)
public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String token = getTokenFromCookie(httpReq, "jwt_token");
        if (token != null) {
            try {
                DecodedJWT decodedJWT = JWTUtils.verifyToken(token);
                String username = decodedJWT.getSubject();
                String role = decodedJWT.getClaim("role").asString();

                // Nếu trang /admin.jsp mà role != ADMIN => chặn
                String uri = httpReq.getRequestURI();
                if (uri.endsWith("/admin.jsp") && !"ADMIN".equals(role)) {
                    httpRes.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return;
                }

                httpReq.setAttribute("username", username);
                httpReq.setAttribute("role", role);

                chain.doFilter(request, response);
                return;
            } catch (Exception e) {
            }
        }
        httpRes.sendRedirect(httpReq.getContextPath() + "/login.jsp");
    }

    @Override
    public void destroy() {
    }

    private String getTokenFromCookie(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
