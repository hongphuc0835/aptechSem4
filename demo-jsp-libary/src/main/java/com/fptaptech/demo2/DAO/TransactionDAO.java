package com.fptaptech.demo2.DAO;

import com.fptaptech.demo2.model.Transaction;
import com.fptaptech.demo2.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // ✅ Mượn sách
    public boolean borrowBook(int userId, int bookId) {
        String query = """
            INSERT INTO transactions (user_id, book_id, borrow_date, due_date, status) 
            VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 14 DAY), 'BORROWED')
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0; // ✅ Trả về `true` nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Trả sách
    public boolean returnBook(int transactionId) {
        String query = """
            UPDATE transactions 
            SET return_date = NOW(), status = 'RETURNED' 
            WHERE id = ? AND status = 'BORROWED'
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transactionId);
            return stmt.executeUpdate() > 0; // ✅ Trả về `true` nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Lấy danh sách giao dịch của user (hoặc tất cả nếu userId == -1)
    public List<Transaction> getUserTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = """
        SELECT t.id, t.user_id, u.username AS user_name, t.book_id, 
               COALESCE(b.title, 'Sách đã bị xóa') AS book_title,
               t.borrow_date, t.due_date, t.return_date, t.status
        FROM transactions t
        LEFT JOIN books b ON t.book_id = b.id
        LEFT JOIN users u ON t.user_id = u.id
        """ + (userId != -1 ? " WHERE t.user_id = ?" : "") + " ORDER BY t.borrow_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (userId != -1) {
                stmt.setInt(1, userId);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // ✅ Xử lý `return_date` để tránh lỗi NULL
                Timestamp returnTimestamp = rs.getTimestamp("return_date");
                LocalDateTime returnDate = (returnTimestamp != null) ? returnTimestamp.toLocalDateTime() : null;

                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"), // ✅ Thêm tên người mượn
                        rs.getInt("book_id"),
                        rs.getString("book_title"), // ✅ Thêm tên sách
                        rs.getTimestamp("borrow_date").toLocalDateTime(),
                        rs.getTimestamp("due_date") != null ? rs.getTimestamp("due_date").toLocalDateTime() : null,
                        returnDate,  // ✅ Giải quyết lỗi return_date bị NULL
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }



    public boolean deleteTransaction(int transactionId) {
        String query = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transactionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
