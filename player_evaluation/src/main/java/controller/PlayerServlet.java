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
import java.util.List;

@WebServlet("/player")
public class PlayerServlet extends HttpServlet {
    private PlayerDAO playerDAO;
    private IndexerDAO indexerDAO;

    @Override
    public void init() {
        playerDAO = new PlayerDAO();
        indexerDAO = new IndexerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                action = "list";
            }

            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deletePlayer(request, response);
                    break;
                default:
                    listPlayers(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                createPlayer(request, response);
            } else if ("edit".equals(action)) {
                updatePlayer(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Hiển thị danh sách player
    private void listPlayers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Player> players = playerDAO.getAllPlayersWithIndex();
        request.setAttribute("players", players);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/player/player.jsp");
        dispatcher.forward(request, response);
    }

    // Hiển thị form tạo player
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("player", null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/player/player.jsp");
        dispatcher.forward(request, response);
    }

    // Hiển thị form sửa player
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int playerId = Integer.parseInt(request.getParameter("id"));
        Player player = playerDAO.getPlayerById(playerId);
        request.setAttribute("player", player);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/player/player.jsp");
        dispatcher.forward(request, response);
    }

    // Xử lý tạo player
    private void createPlayer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String playerName = request.getParameter("playerName");
        String playerAge = request.getParameter("playerAge");
        String indexName = request.getParameter("indexName");
        float value = Float.parseFloat(request.getParameter("value"));

        int indexId = indexerDAO.getIndexIdByName(indexName);
        Player player = new Player();
        player.setName(playerName);
        player.setFullName(playerName);
        player.setAge(playerAge);
        player.setIndexId(indexId);

        playerDAO.createPlayer(player, value);
        response.sendRedirect("player");
    }

    // Xử lý cập nhật player
    private void updatePlayer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
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
        response.sendRedirect("player");
    }

    // Xử lý xóa player
    private void deletePlayer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int playerId = Integer.parseInt(request.getParameter("id"));
        playerDAO.deletePlayer(playerId);
        response.sendRedirect("player");
    }
}
