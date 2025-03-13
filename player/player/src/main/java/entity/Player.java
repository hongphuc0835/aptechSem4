package entity;

import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerId;
    private String name;
    private String fullName;
    private int age;  // Changed back to int to match original functionality
    private int indexId;

    // Constructors
    public Player() {}

    public Player(String name, String fullName, int age, int indexId) {
        this.name = name;
        this.fullName = fullName;
        this.age = age;
        this.indexId = indexId;
    }

    // Getters and Setters
    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getIndexId() { return indexId; }
    public void setIndexId(int indexId) { this.indexId = indexId; }

    // Nested PlayerIndex class
    public static class PlayerIndex {
        private int id;
        private String playerName;
        private int age;
        private String indexName;
        private float value;

        public PlayerIndex(int id, String playerName, int age, String indexName, float value) {
            this.id = id;
            this.playerName = playerName;
            this.age = age;
            this.indexName = indexName;
            this.value = value;
        }

        // Getters
        public int getId() { return id; }
        public String getPlayerName() { return playerName; }
        public int getAge() { return age; }
        public String getIndexName() { return indexName; }
        public float getValue() { return value; }
    }

    // Get all players
    public List<PlayerIndex> getAll() {
        List<PlayerIndex> playerIndices = new ArrayList<>();
        String sql = "SELECT p.player_id, p.name AS player_name, p.age, i.name AS index_name, pi.value " +
                "FROM player p " +
                "LEFT JOIN player_index pi ON p.player_id = pi.player_id " +
                "LEFT JOIN indexer i ON pi.index_id = i.index_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String ageStr = rs.getString("age");
                int age = 0;
                try {
                    age = ageStr != null ? Integer.parseInt(ageStr) : 0;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid age format for player: " + rs.getString("player_name"));
                }

                PlayerIndex playerIndex = new PlayerIndex(
                        rs.getInt("player_id"),
                        rs.getString("player_name"),
                        age,
                        rs.getString("index_name"),
                        rs.getFloat("value")
                );
                playerIndices.add(playerIndex);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return playerIndices;
    }
    // Add a new player with their index
    public void add(String playerName, int age, String indexName, float value)
            throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtil.getConnection()) {
            // Insert into player table
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
        }
    }
  }