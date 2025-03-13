package controller;

import entity.Player;
import util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/AddPlayerServlet")
public class AddPlayerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String playerName = request.getParameter("playerName");
        int age = Integer.parseInt(request.getParameter("age"));
        String indexName = request.getParameter("indexName");
        float value = Float.parseFloat(request.getParameter("value"));

        // Insert the player into the database
        try (Connection conn = DBUtil.getConnection()) {
            // First, insert into the player table
            String insertPlayerSQL = "INSERT INTO player (name, age) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertPlayerSQL,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, playerName);
            stmt.setInt(2, age);
            stmt.executeUpdate();

            // Get the generated player_id
            int playerId;
            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    playerId = rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve player ID.");
                }
            }

            // Get the index_id based on indexName
            String getIndexIdSQL = "SELECT index_id FROM indexer WHERE name = ?";
            PreparedStatement indexStmt = conn.prepareStatement(getIndexIdSQL);
            indexStmt.setString(1, indexName);
            int indexId;
            try (var rs = indexStmt.executeQuery()) {
                if (rs.next()) {
                    indexId = rs.getInt("index_id");
                } else {
                    throw new SQLException("Index name not found: " + indexName);
                }
            }

            // Insert into player_index table
            String insertPlayerIndexSQL = "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)";
            PreparedStatement playerIndexStmt = conn.prepareStatement(insertPlayerIndexSQL);
            playerIndexStmt.setInt(1, playerId);
            playerIndexStmt.setInt(2, indexId);
            playerIndexStmt.setFloat(3, value);
            playerIndexStmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error adding player: " + e.getMessage());
            request.getRequestDispatcher("player.jsp").forward(request, response);
            return;
        }

        // Redirect back to the player list page
        response.sendRedirect("PlayerListServlet");
    }
}