<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fptaptech.demo2.model.Book" %>
<%@ page import="com.fptaptech.demo2.Service.BookService" %>
<%
    // Get book ID from request
    int bookId = Integer.parseInt(request.getParameter("id"));

    // Retrieve book information from database
    BookService bookService = new BookService();
    Book book = bookService.getBookById(bookId);

    if (book == null) {
        response.sendRedirect("admin.jsp"); // Redirect to the list if the book is not found
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>✏️ Edit Book</title>

    <!-- ✅ Add Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ✅ Add FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        h2 {
            color: #007bff;
            text-align: center;
        }
        label {
            font-weight: bold;
            margin-top: 10px;
        }
        .form-control {
            margin-bottom: 10px;
        }
        .btn-primary {
            width: 100%;
            font-size: 16px;
        }
        .back a {
            display: block;
            text-align: center;
            margin-top: 15px;
            text-decoration: none;
            color: #007bff;
        }
        .back a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>✏️ Edit Book</h2>
    <form action="editBook" method="post">
        <input type="hidden" name="id" value="<%= book.getId() %>">

        <label for="title">Book Title:</label>
        <input type="text" id="title" name="title" class="form-control" value="<%= book.getTitle() %>" required>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" class="form-control" value="<%= book.getAuthor() %>" required>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity" class="form-control" value="<%= book.getQuantity() %>" min="0" required>

        <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Update</button>
    </form>

    <div class="back">
        <a href="admin.jsp">⬅ Back to list</a>
    </div>
</div>

<!-- ✅ Add Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
