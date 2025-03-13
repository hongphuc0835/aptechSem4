package controller;

import dao.PlayerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/player/delete")
public class PlayerDeleteServlet extends HttpServlet {
    private PlayerDAO playerDAO;

    public void init() {
        playerDAO = new PlayerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int playerId = Integer.parseInt(request.getParameter("id"));
            playerDAO.deletePlayer(playerId);
            response.sendRedirect("/player");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }
    }
}
