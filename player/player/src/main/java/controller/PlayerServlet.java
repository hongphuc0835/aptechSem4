package controller;

import entity.Player;
import entity.Player.PlayerIndex;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlayerServlet", urlPatterns = {"/player"})
public class PlayerServlet extends HttpServlet {
    private Player playerModel;

    @Override
    public void init() throws ServletException {
        playerModel = new Player();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch all player-index data
        List<PlayerIndex> playerIndices = playerModel.getAll();
        request.setAttribute("playerIndices", playerIndices);

        // Forward to JSP
        request.getRequestDispatcher("player.jsp").forward(request, response);
    }


}