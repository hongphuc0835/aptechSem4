<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>🏠 Home</title>

    <!-- ✅ Add Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ✅ Add FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
        }
        h1 {
            color: #007bff;
            margin-bottom: 20px;
        }
        .btn-primary {
            font-size: 18px;
            padding: 10px 20px;
            border-radius: 5px;
        }
        .btn-primary i {
            margin-right: 8px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>🚀 Welcome to JWT MVC with Annotation</h1>
    <p>Login system with JSON Web Token</p>
    <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary">
        <i class="fas fa-sign-in-alt"></i> Go to Login Page
    </a>
</div>

<!-- ✅ Add Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
