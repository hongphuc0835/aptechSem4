package com.fptaptech.demo2.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int quantity;
    private String status;

    public Book(int id, String title, String author, int quantity, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
