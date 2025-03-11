<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>🔐 Login</title>

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
        }
        .login-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
            width: 350px;
            text-align: center;
        }
        h2 {
            color: #007bff;
            margin-bottom: 20px;
        }
        .form-control {
            margin-bottom: 15px;
        }
        .btn-primary {
            width: 100%;
            font-size: 16px;
            padding: 10px;
        }
        .btn-primary i {
            margin-right: 8px;
        }
        .error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2><i class="fas fa-user-lock"></i> Login</h2>

    <form action="${pageContext.request.contextPath}/auth" method="post">
        <div class="mb-3">
            <input type="text" name="username" class="form-control" placeholder="Username" required>
        </div>

        <div class="mb-3">
            <input type="password" name="password" class="form-control" placeholder="Password" required>
        </div>

        <button type="submit" class="btn btn-primary"><i class="fas fa-sign-in-alt"></i> Login</button>
    </form>

    <!-- Display error message (if any) -->
    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>
</div>

<!-- ✅ Add Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
