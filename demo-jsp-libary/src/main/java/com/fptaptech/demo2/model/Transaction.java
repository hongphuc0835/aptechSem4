package com.fptaptech.demo2.model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int userId;
    private String userName;  // ✅ Thêm thuộc tính lưu tên người mượn
    private int bookId;
    private String bookTitle; // ✅ Thêm thuộc tính lưu tên sách
    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private String status;

    // ✅ Constructor đầy đủ thông tin
    public Transaction(int id, int userId, String userName, int bookId, String bookTitle,
                       LocalDateTime borrowDate, LocalDateTime dueDate, LocalDateTime returnDate, String status) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // ✅ Getter & Setter cho tất cả thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() { // ✅ Getter cho tên người mượn
        return userName;
    }

    public void setUserName(String userName) { // ✅ Setter cho tên người mượn
        this.userName = userName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() { // ✅ Getter cho tên sách
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) { // ✅ Setter cho tên sách
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
