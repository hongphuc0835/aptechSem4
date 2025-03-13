package entity;

import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerIndex {
    private int id;
    private int playerId;
    private int indexId;
    private float value;

    public PlayerIndex() {}

    public PlayerIndex(int playerId, int indexId, float value) {
        this.playerId = playerId;
        this.indexId = indexId;
        this.value = value;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public int getIndexId() { return indexId; }
    public void setIndexId(int indexId) { this.indexId = indexId; }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    // Phương thức lấy tất cả player indices từ database
    public List<PlayerIndex> getAllPlayerIndices() {
        List<PlayerIndex> playerIndices = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, player_id, index_id, value FROM player_index")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PlayerIndex playerIndex = new PlayerIndex();
                playerIndex.setId(rs.getInt("id"));
                playerIndex.setPlayerId(rs.getInt("player_id"));
                playerIndex.setIndexId(rs.getInt("index_id"));
                playerIndex.setValue(rs.getFloat("value"));
                playerIndices.add(playerIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerIndices;
    }

    // Phương thức thêm player index vào database
    public void save() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO player_index (player_id, index_id, value) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.playerId);
            stmt.setInt(2, this.indexId);
            stmt.setFloat(3, this.value);
            stmt.executeUpdate();

            // Lấy ID tự động sinh ra
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức cập nhật player index trong database
    public void update() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE player_index SET player_id = ?, index_id = ?, value = ? WHERE id = ?")) {
            stmt.setInt(1, this.playerId);
            stmt.setInt(2, this.indexId);
            stmt.setFloat(3, this.value);
            stmt.setInt(4, this.id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức xóa player index khỏi database
    public void delete() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM player_index WHERE id = ?")) {
            stmt.setInt(1, this.id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức tìm player index theo ID
    public static PlayerIndex findById(int id) {
        PlayerIndex playerIndex = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, player_id, index_id, value FROM player_index WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                playerIndex = new PlayerIndex();
                playerIndex.setId(rs.getInt("id"));
                playerIndex.setPlayerId(rs.getInt("player_id"));
                playerIndex.setIndexId(rs.getInt("index_id"));
                playerIndex.setValue(rs.getFloat("value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerIndex;
    }
}