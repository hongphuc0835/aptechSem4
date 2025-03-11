package com.fptaptech.demo2.Service;

import com.fptaptech.demo2.DAO.BookDAO;
import com.fptaptech.demo2.model.Book;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO = new BookDAO();

    // 1️⃣ Lấy danh sách tất cả sách
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    // 2️⃣ Thêm sách mới
    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty() ||
                book.getAuthor() == null || book.getAuthor().trim().isEmpty() ||
                book.getQuantity() < 0) {
            return false; // Tránh thêm sách không hợp lệ
        }
        return bookDAO.addBook(book);
    }

    // 3️⃣ Sửa thông tin sách
    public boolean updateBook(Book book) {
        if (book.getId() <= 0 || book.getTitle() == null || book.getTitle().trim().isEmpty() ||
                book.getAuthor() == null || book.getAuthor().trim().isEmpty() ||
                book.getQuantity() < 0) {
            return false; // Tránh sửa sách không hợp lệ
        }
        return bookDAO.updateBook(book);
    }

    // 4️⃣ Xóa sách theo ID
    public boolean deleteBook(int bookId) {
        if (bookId <= 0) {
            return false; // Tránh xóa sách với ID không hợp lệ
        }
        return bookDAO.deleteBook(bookId);
    }

    // 5️⃣ Cập nhật số lượng sách khi mượn/trả
    public boolean updateBookQuantity(int bookId, int newQuantity) {
        if (bookId <= 0 || newQuantity < 0) {
            return false; // Tránh cập nhật số lượng không hợp lệ
        }
        return bookDAO.updateBookQuantity(bookId, newQuantity);
    }
    
    public Book getBookById(int bookId) {
        return bookDAO.getBookById(bookId);
    }

}
