<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.fptaptech.demo2.model.Book, com.fptaptech.demo2.model.Transaction" %>
<%@ page import="com.fptaptech.demo2.Service.BookService, com.fptaptech.demo2.Service.TransactionService" %>
<%
    BookService bookService = new BookService();
    TransactionService transactionService = new TransactionService();

    List<Book> books = bookService.getAllBooks();
    List<Transaction> transactions = transactionService.getUserTransactions(-1);
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Management - Admin</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font Awesome -->
    <script src="https://kit.fontawesome.com/YOUR-FONT-AWESOME-KEY.js" crossorigin="anonymous"></script>

    <!-- Custom CSS -->
    <style>
        body {
            background: linear-gradient(120deg, #f6f9fc, #cfd9df);
            font-family: "Poppins", sans-serif;
        }
        .custom-card {
            border-radius: 15px;
            overflow: hidden;
            transition: transform 0.3s ease-in-out;
        }
        .custom-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
        }
        .btn-custom {
            border-radius: 8px;
            transition: all 0.3s ease;
        }
        .btn-custom:hover {
            transform: scale(1.05);
        }
        table {
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }
        table tr:hover {
            background: #f8f9fa;
        }
    </style>
</head>
<body class="bg-light">

<div class="container py-5">

    <!-- HEADER -->
    <div class="text-center mb-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2 class="text-primary">📚 Book Library</h2>
            <p>Welcome, <strong><%= (session.getAttribute("adminName") != null) ? session.getAttribute("adminName") : "Admin" %></strong> |
                <a href="logout" class="btn btn-danger">Logout</a>
            </p>
        </div>
    </div>
    <!-- NOTIFICATION -->
    <% if (message != null) { %>
    <div class="alert <%= message.contains("failed") ? "alert-danger" : "alert-success" %> text-center">
        <%= message %>
    </div>
    <% } %>

    <!-- ADD BOOK FORM -->
    <div class="card shadow-lg custom-card p-4 mb-4">
        <h3 class="text-success"><i class="fas fa-plus-circle"></i> Add New Book</h3>
        <form action="addBook" method="post">
            <div class="mb-3">
                <input type="text" name="title" placeholder="Book Title" required class="form-control">
            </div>
            <div class="mb-3">
                <input type="text" name="author" placeholder="Author" required class="form-control">
            </div>
            <div class="mb-3">
                <input type="number" name="quantity" placeholder="Quantity" min="1" required class="form-control">
            </div>
            <button type="submit" class="btn btn-success w-100 btn-custom">
                <i class="fas fa-check"></i> Add
            </button>
        </form>
    </div>

    <!-- BOOK LIST -->
    <div class="card shadow-lg custom-card p-4 mb-4">
        <h3 class="text-primary"><i class="fas fa-book-open"></i> Book List</h3>
        <% if (books.isEmpty()) { %>
        <p class="text-danger">⚠️ No books available in the system.</p>
        <% } else { %>
        <table class="table table-hover">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Quantity</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Book book : books) { %>
            <tr>
                <td><%= book.getId() %></td>
                <td><%= book.getTitle() %></td>
                <td><%= book.getAuthor() %></td>
                <td><%= book.getQuantity() %></td>
                <td class="<%= book.getStatus().equals("AVAILABLE") ? "text-success" : "text-danger" %>">
                    <%= book.getStatus().equals("AVAILABLE") ? "Available" : "Out of Stock" %>
                </td>
                <td>
                    <a href="editBook.jsp?id=<%= book.getId() %>" class="btn btn-warning btn-sm">✏️ Edit</a>
                    <a href="deleteBook?id=<%= book.getId() %>" onclick="return confirm('Delete this book?');" class="btn btn-danger btn-sm">🗑️ Delete</a>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>

    <!-- TRANSACTION HISTORY -->
    <div class="card shadow-lg custom-card p-4">
        <h3 class="text-warning"><i class="fas fa-history"></i> Borrow/Return History</h3>
        <% if (transactions == null || transactions.isEmpty()) { %>
        <p class="text-danger">⚠️ No transactions found.</p>
        <% } else { %>
        <table class="table table-hover">
            <thead class="table-dark">
            <tr>
                <th>Borrower</th>
                <th>Book Title</th>
                <th>Borrow Date</th>
                <th>Due Date</th>
                <th>Return Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Transaction t : transactions) { %>
            <tr>
                <td><%= t.getUserName() %></td>
                <td><%= t.getBookTitle() %></td>
                <td><%= t.getBorrowDate() %></td>
                <td><%= t.getDueDate() != null ? t.getDueDate() : "None" %></td>
                <td><%= t.getReturnDate() != null ? t.getReturnDate() : "Not Returned" %></td>
                <td class="<%= "BORROWED".equals(t.getStatus()) ? "text-warning" : "text-success" %>">
                    <%= "BORROWED".equals(t.getStatus()) ? "Borrowed" : "Returned" %>
                </td>
                <td>
                    <form action="deleteTransaction" method="post" onsubmit="return confirm('Are you sure you want to delete this transaction?');">
                        <input type="hidden" name="transactionId" value="<%= t.getId() %>">
                        <button type="submit" class="btn btn-danger btn-sm">🗑️ Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>
</body>
</html>
