package com.fptaptech.demo2.DAO;

import com.fptaptech.demo2.model.Book;
import com.fptaptech.demo2.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 1️⃣ Lấy danh sách tất cả sách
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // 2️⃣ Thêm sách mới
    public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, quantity, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getQuantity());
            stmt.setString(4, book.getQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3️⃣ Sửa thông tin sách
    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, quantity = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getQuantity());
            stmt.setString(4, book.getQuantity() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
            stmt.setInt(5, book.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4️⃣ Xóa sách theo ID
    public boolean deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5️⃣ Cập nhật số lượng sách sau khi mượn/trả
    public boolean updateBookQuantity(int bookId, int newQuantity) {
        String query = "UPDATE books SET quantity = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String status = (newQuantity > 0) ? "AVAILABLE" : "OUT_OF_STOCK";

            stmt.setInt(1, newQuantity);
            stmt.setString(2, status);
            stmt.setInt(3, bookId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Book getBookById(int bookId) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
