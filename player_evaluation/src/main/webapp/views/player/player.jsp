<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Player Information</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
</head>

<body class="bg-light">

<div class="container py-5">
    <h2 class="text-center mb-4 fw-bold text-warning">Player Information</h2>

    <!-- Form Create/Edit -->
    <div class="card p-4 mb-4 shadow-sm">
        <form action="player" method="post" class="row g-3">
            <input type="hidden" name="action" value="${player != null ? 'edit' : 'create'}">
            <c:if test="${player != null}">
                <input type="hidden" name="playerId" value="${player.playerId}">
            </c:if>

            <!-- Player Name -->
            <div class="col-md-3">
                <label class="form-label fw-semibold">Player name</label>
                <input type="text" class="form-control" name="playerName" value="${player.name}" placeholder="Player name" required>
            </div>

            <!-- Player Age -->
            <div class="col-md-3">
                <label class="form-label fw-semibold">Player age</label>
                <input type="text" class="form-control" name="playerAge" value="${player.age}" placeholder="Player age" required>
            </div>

            <!-- Index Name -->
            <div class="col-md-3">
                <label class="form-label fw-semibold">Index name</label>
                <select name="indexName" class="form-select" required>
                    <option value="">Index name</option>
                    <option value="speed" ${player.indexName == 'speed' ? 'selected' : ''}>speed</option>
                    <option value="strength" ${player.indexName == 'strength' ? 'selected' : ''}>strength</option>
                    <option value="accurate" ${player.indexName == 'accurate' ? 'selected' : ''}>accurate</option>
                </select>
            </div>

            <!-- Value -->
            <div class="col-md-2">
            <label class="form-label fw-semibold">Value</label>
            <input type="number" class="form-control" name="value" value="${player.value}" placeholder="Enter value" required>
        </div>

            <!-- Submit Button -->
            <div class="col-md-1 d-flex align-items-end">
                <button type="submit" class="btn btn-danger w-100">${player != null ? 'Update' : 'Add'}</button>
            </div>
        </form>
    </div>

    <!-- Player List -->
    <div class="table-responsive">
        <table class="table table-bordered table-hover shadow-sm">
            <thead class="bg-danger text-white text-center">
            <tr>
                <th>Id</th>
                <th>Player name</th>
                <th>Player age</th>
                <th>Index name</th>
                <th>Value</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="player" items="${players}">
                <tr>
                    <td class="text-center">${player.playerId}</td>
                    <td>${player.name}</td>
                    <td class="text-center">${player.age}</td>
                    <td>${player.indexName}</td>
                    <td class="text-center">${player.value}</td>
                    <td class="text-center">
                        <a href="player?action=edit&id=${player.playerId}" class="text-decoration-none me-2">
                            <img src="https://cdn-icons-png.flaticon.com/16/1160/1160515.png" alt="Edit">
                        </a>
                        <a href="player?action=delete&id=${player.playerId}"
                           class="text-decoration-none"
                           onclick="return confirm('Are you sure?')">
                            <img src="https://cdn-icons-png.flaticon.com/16/1214/1214428.png" alt="Delete">
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Footer -->
    <footer class="text-center mt-4 text-white bg-danger py-2 rounded">
        Số 8, Tôn Thất Thuyết, Cầu Giấy, Hà Nội
    </footer>

</div>

</body>

</html>
