package controller;

import dao.PlayerDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Player;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/player")
public class PlayerListServlet extends HttpServlet {
    private PlayerDAO playerDAO;

    public void init() {
        playerDAO = new PlayerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Player> players = playerDAO.getAllPlayersWithIndex();
            request.setAttribute("players", players);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/player/list.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
