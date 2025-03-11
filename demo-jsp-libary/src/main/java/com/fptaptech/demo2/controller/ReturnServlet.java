package com.fptaptech.demo2.controller;

import com.fptaptech.demo2.Service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ReturnServlet", urlPatterns = {"/return"})
public class ReturnServlet extends HttpServlet {
    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int transactionId = Integer.parseInt(request.getParameter("transactionId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        Optional<String> result = transactionService.returnBook(transactionId, bookId);
        request.setAttribute("message", result.get());
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }
}
