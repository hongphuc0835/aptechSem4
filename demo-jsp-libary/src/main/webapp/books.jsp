<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.fptaptech.demo2.model.Book, com.fptaptech.demo2.model.Transaction" %>
<%@ page import="com.fptaptech.demo2.Service.BookService, com.fptaptech.demo2.Service.TransactionService" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession sessionUser = request.getSession();
    String username = (String) sessionUser.getAttribute("username");
    int userId = (sessionUser.getAttribute("userId") != null) ? (int) sessionUser.getAttribute("userId") : -1;

    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    BookService bookService = new BookService();
    TransactionService transactionService = new TransactionService();

    List<Book> books = bookService.getAllBooks();
    List<Transaction> transactions = transactionService.getUserTransactions(userId);
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>📚 Library System</title>

    <!-- ✅ Add Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ✅ Add FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
        h2, h3 {
            color: #343a40;
        }
        .table th, .table td {
            text-align: center;
            vertical-align: middle;
        }
        .logout {
            text-decoration: none;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            background-color: red;
        }
        .logout:hover {
            background-color: darkred;
        }
        .btn {
            font-size: 14px;
            padding: 6px 12px;
        }
    </style>
</head>
<body class="bg-light">

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="text-primary">📚 Library System</h2>
        <p>Welcome, <strong><%= username %></strong> | <a href="logout" class="btn btn-danger">Logout</a></p>
    </div>

    <% if (message != null) { %>
    <div class="alert <%= message.contains("failed") ? "alert-danger" : "alert-success" %>">
        <%= message %>
    </div>
    <% } %>

    <h3 class="text-dark">📖 Book List</h3>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Book book : books) { %>
        <tr>
            <td><%= book.getTitle() %></td>
            <td><%= book.getAuthor() %></td>
            <td><%= book.getQuantity() %></td>
            <td>
                    <span class="badge <%= "AVAILABLE".equals(book.getStatus()) ? "bg-success" : "bg-danger" %>">
                        <%= book.getStatus().equals("AVAILABLE") ? "Available" : "Out of stock" %>
                    </span>
            </td>
            <td>
                <% if ("AVAILABLE".equals(book.getStatus())) { %>
                <form action="borrow" method="post">
                    <input type="hidden" name="userId" value="<%= userId %>">
                    <input type="hidden" name="bookId" value="<%= book.getId() %>">
                    <button type="submit" class="btn btn-primary"><i class="fas fa-book"></i> Borrow</button>
                </form>
                <% } else { %>
                <span class="text-danger">❌ Out of stock</span>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <h3 class="text-dark">📜 My Borrowing/Return History</h3>
    <% if (transactions.isEmpty()) { %>
    <div class="alert alert-warning">⚠️ You have not borrowed any books.</div>
    <% } else { %>
    <table class="table table-striped">
        <thead class="table-dark">
        <tr>
            <th>Book Title</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Transaction t : transactions) { %>
        <tr>
            <td><%= t.getBookTitle() %></td>
            <td><%= t.getBorrowDate() != null ? t.getBorrowDate() : "N/A" %></td>
            <td><%= t.getDueDate() != null ? t.getDueDate() : "N/A" %></td>
            <td><%= t.getReturnDate() != null ? t.getReturnDate() : "Not returned" %></td>
            <td>
                    <span class="badge <%= "BORROWED".equals(t.getStatus()) ? "bg-warning" : "bg-success" %>">
                        <%= "BORROWED".equals(t.getStatus()) ? "Borrowed" : "Returned" %>
                    </span>
            </td>
            <td>
                <% if ("BORROWED".equals(t.getStatus())) { %>
                <form action="return" method="post">
                    <input type="hidden" name="transactionId" value="<%= t.getId() %>">
                    <input type="hidden" name="bookId" value="<%= t.getBookId() %>">
                    <button type="submit" class="btn btn-success"><i class="fas fa-undo"></i> Return</button>
                </form>
                <% } else { %>
                <span class="text-success">✅ Returned</span>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>

</div>

<!-- ✅ Add Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
