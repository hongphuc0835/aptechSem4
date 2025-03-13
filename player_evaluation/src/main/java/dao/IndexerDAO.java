package dao;

import model.Indexer;
import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndexerDAO {
    public List<Indexer> getAllIndexers() throws SQLException {
        List<Indexer> indexers = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM indexer";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Indexer indexer = new Indexer();
            indexer.setIndexId(rs.getInt("index_id"));
            indexer.setName(rs.getString("name"));
            indexer.setValueMin(rs.getFloat("valueMin"));
            indexer.setValueMax(rs.getFloat("valueMax"));
            indexers.add(indexer);
        }
        rs.close();
        stmt.close();
        conn.close();
        return indexers;
    }

    public int getIndexIdByName(String indexName) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT index_id FROM indexer WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, indexName);
        ResultSet rs = pstmt.executeQuery();

        int indexId = 0;
        if (rs.next()) {
            indexId = rs.getInt("index_id");
        }
        rs.close();
        pstmt.close();
        conn.close();
        return indexId;
    }
}