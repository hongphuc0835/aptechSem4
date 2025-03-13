package controller;

import dao.PlayerDAO;
import dao.IndexerDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Player;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/player/edit")
public class PlayerEditServlet extends HttpServlet {
    private PlayerDAO playerDAO;
    private IndexerDAO indexerDAO;

    public void init() {
        playerDAO = new PlayerDAO();
        indexerDAO = new IndexerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int playerId = Integer.parseInt(request.getParameter("id"));
            Player existingPlayer = playerDAO.getPlayerById(playerId);
            request.setAttribute("player", existingPlayer);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/player/edit.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int playerId = Integer.parseInt(request.getParameter("playerId"));
            String playerName = request.getParameter("playerName");
            String playerAge = request.getParameter("playerAge");
            String indexName = request.getParameter("indexName");
            float value = Float.parseFloat(request.getParameter("value"));

            int indexId = indexerDAO.getIndexIdByName(indexName);

            Player player = new Player();
            player.setPlayerId(playerId);
            player.setName(playerName);
            player.setFullName(playerName);
            player.setAge(playerAge);
            player.setIndexId(indexId);

            playerDAO.updatePlayer(player, value);
            response.sendRedirect("/player");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
