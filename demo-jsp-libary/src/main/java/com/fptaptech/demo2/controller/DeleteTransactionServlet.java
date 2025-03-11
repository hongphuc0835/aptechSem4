package com.fptaptech.demo2.controller;

import com.fptaptech.demo2.Service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeleteTransactionServlet", urlPatterns = {"/deleteTransaction"})
public class DeleteTransactionServlet extends HttpServlet {
    private final TransactionService transactionService = new TransactionService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int transactionId = Integer.parseInt(request.getParameter("transactionId"));

        boolean success = transactionService.deleteTransaction(transactionId);
        if (success) {
            request.setAttribute("message", "✅ Delete loan history successfully!");
        } else {
            request.setAttribute("message", "❌ Delete failed, please try again!");
        }
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
}
