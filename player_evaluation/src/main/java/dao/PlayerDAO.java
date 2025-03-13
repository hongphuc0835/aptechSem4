package dao;

import model.Player;
import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    public List<Player> getAllPlayersWithIndex() throws SQLException {
        List<Player> players = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT p.player_id, p.name, p.age, i.name AS indexName, pi.value " +
                "FROM player p " +
                "JOIN player_index pi ON p.player_id = pi.player_id " +
                "JOIN indexer i ON pi.index_id = i.index_id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Player player = new Player();
            player.setPlayerId(rs.getInt("player_id"));
            player.setName(rs.getString("name"));
            player.setAge(rs.getString("age"));
            player.setIndexName(rs.getString("indexName"));
            player.setValue(rs.getFloat("value"));
            players.add(player);
        }
        rs.close();
        stmt.close();
        conn.close();
        return players;
    }

    public Player getPlayerById(int playerId) throws SQLException {
        Player player = null;
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT p.player_id, p.name, p.full_name, p.age, i.name AS indexName, pi.value " +
                "FROM player p " +
                "JOIN player_index pi ON p.player_id = pi.player_id " +
                "JOIN indexer i ON pi.index_id = i.index_id " +
                "WHERE p.player_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, playerId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            player = new Player();
            player.setPlayerId(rs.getInt("player_id"));
            player.setName(rs.getString("name"));
            player.setFullName(rs.getString("full_name"));
            player.setAge(rs.getString("age"));
            player.setIndexName(rs.getString("indexName"));
            player.setValue(rs.getFloat("value"));
        }
        rs.close();
        pstmt.close();
        conn.close();
        return player;
    }

    public void createPlayer(Player player, float value) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "INSERT INTO player (name, full_name, age, index_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getFullName());
            pstmt.setString(3, player.getAge());
            pstmt.setInt(4, player.getIndexId());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int playerId = 0;
            if (generatedKeys.next()) {
                playerId = generatedKeys.getInt(1);
            }

            sql = "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, player.getIndexId());
            pstmt.setFloat(3, value);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void updatePlayer(Player player, float value) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "UPDATE player SET name = ?, full_name = ?, age = ?, index_id = ? WHERE player_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getName());
            pstmt.setString(2, player.getFullName());
            pstmt.setString(3, player.getAge());
            pstmt.setInt(4, player.getIndexId());
            pstmt.setInt(5, player.getPlayerId());
            pstmt.executeUpdate();

            sql = "UPDATE player_index SET index_id = ?, value = ? WHERE player_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, player.getIndexId());
            pstmt.setFloat(2, value);
            pstmt.setInt(3, player.getPlayerId());
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void deletePlayer(int playerId) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);
        try {
            String sql = "DELETE FROM player_index WHERE player_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playerId);
            pstmt.executeUpdate();

            sql = "DELETE FROM player WHERE player_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playerId);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}