package entity;

import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Indexer {
    private int indexId;
    private String name;
    private float valueMin;
    private float valueMax;

    public Indexer() {}

    public Indexer(String name, float valueMin, float valueMax) {
        this.name = name;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    // Getters and Setters
    public int getIndexId() { return indexId; }
    public void setIndexId(int indexId) { this.indexId = indexId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getValueMin() { return valueMin; }
    public void setValueMin(float valueMin) { this.valueMin = valueMin; }

    public float getValueMax() { return valueMax; }
    public void setValueMax(float valueMax) { this.valueMax = valueMax; }

    // Phương thức lấy tất cả indexers từ database
    public List<Indexer> getAllIndexers() {
        List<Indexer> indexers = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT index_id, name, valueMin, valueMax FROM indexer")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Indexer indexer = new Indexer();
                indexer.setIndexId(rs.getInt("index_id"));
                indexer.setName(rs.getString("name"));
                indexer.setValueMin(rs.getFloat("valueMin"));
                indexer.setValueMax(rs.getFloat("valueMax"));
                indexers.add(indexer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexers;
    }

    // Phương thức thêm indexer vào database
    public void save() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO indexer (name, valueMin, valueMax) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.name);
            stmt.setFloat(2, this.valueMin);
            stmt.setFloat(3, this.valueMax);
            stmt.executeUpdate();

            // Lấy ID tự động sinh ra
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.indexId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức cập nhật indexer trong database
    public void update() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE indexer SET name = ?, valueMin = ?, valueMax = ? WHERE index_id = ?")) {
            stmt.setString(1, this.name);
            stmt.setFloat(2, this.valueMin);
            stmt.setFloat(3, this.valueMax);
            stmt.setInt(4, this.indexId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức xóa indexer khỏi database
    public void delete() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM indexer WHERE index_id = ?")) {
            stmt.setInt(1, this.indexId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức tìm indexer theo ID
    public static Indexer findById(int indexId) {
        Indexer indexer = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT index_id, name, valueMin, valueMax FROM indexer WHERE index_id = ?")) {
            stmt.setInt(1, indexId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                indexer = new Indexer();
                indexer.setIndexId(rs.getInt("index_id"));
                indexer.setName(rs.getString("name"));
                indexer.setValueMin(rs.getFloat("valueMin"));
                indexer.setValueMax(rs.getFloat("valueMax"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexer;
    }
}