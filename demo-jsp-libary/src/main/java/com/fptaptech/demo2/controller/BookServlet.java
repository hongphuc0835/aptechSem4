package com.fptaptech.demo2.controller;

import com.fptaptech.demo2.model.Book;
import com.fptaptech.demo2.Service.BookService;
import com.fptaptech.demo2.Service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "BookServlet", urlPatterns = {"/books", "/addBook", "/editBook", "/deleteBook"})
public class BookServlet extends HttpServlet {
    private final BookService bookService = new BookService();
    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/books":
                listBooks(request, response);
                break;
            case "/deleteBook":
                deleteBook(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/addBook":
                addBook(request, response);
                break;
            case "/editBook":
                editBook(request, response);
                break;
            case "/borrow":
                borrowBook(request, response);
                break;
            case "/return":
                returnBook(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    // 1. Hiển thị danh sách sách
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Book newBook = new Book(0, title, author, quantity, quantity > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
        boolean success = new BookService().addBook(newBook);

        if (success) {
            request.getSession().setAttribute("message", "Thêm sách thành công!");
            response.sendRedirect("admin.jsp"); // 🔥 CHUYỂN ĐÚNG VỀ DASHBOARD
        } else {
            request.setAttribute("message", "Thêm sách thất bại!");
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        }
    }


    // 3. Sửa sách
    private void editBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Book updatedBook = new Book(id, title, author, quantity, quantity > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
        boolean success = new BookService().updateBook(updatedBook);

        if (success) {
            response.sendRedirect("admin.jsp");
        } else {
            request.setAttribute("message", "Cập nhật sách thất bại!");
            request.getRequestDispatcher("editBook.jsp?id=" + id).forward(request, response);
        }
    }

    // 4. Xóa sách
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = bookService.deleteBook(id);

        if (success) {
            response.sendRedirect("admin.jsp");
        } else {
            request.setAttribute("message", "Xóa sách thất bại!");
            listBooks(request, response);
        }
    }

    // 5. Mượn sách
    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        Optional<String> result = transactionService.borrowBook(userId, bookId);
        request.setAttribute("message", result.get());
        listBooks(request, response);
    }

    // 6. Trả sách
    private void returnBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int transactionId = Integer.parseInt(request.getParameter("transactionId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        Optional<String> result = transactionService.returnBook(transactionId, bookId);
        request.setAttribute("message", result.get());

        // ✅ Chuyển hướng về `books.jsp` thay vì `transactions.jsp`
        listBooks(request, response);
    }

}
