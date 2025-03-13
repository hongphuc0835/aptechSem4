<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>PLAYER INFORMATION</h2>

<div class="form-container">
    <form action="player/create" method="post">
        <div class="form-group">
            <input type="text" name="playerName" placeholder="Player name" required>
            <input type="text" name="playerAge" placeholder="Player age" required>
        </div>
        <div class="form-group">
            <select name="indexName" required>
                <option value="">Index name</option>
                <option value="speed">speed</option>
                <option value="strength">strength</option>
                <option value="accurate">accurate</option>
            </select>
            <select name="value" required>
                <option value="">Value</option>
                <option value="90">90</option>
                <option value="1">1</option>
            </select>
        </div>
        <button type="submit">Add</button>
    </form>
</div>

<table>
    <tr>
        <th>Id</th>
        <th>Player name</th>
        <th>Player age</th>
        <th>Index name</th>
        <th>Value</th>
        <th></th>
    </tr>
    <c:forEach var="player" items="${players}">
        <tr>
            <td>${player.playerId}</td>
            <td>${player.name}</td>
            <td>${player.age}</td>
            <td>${player.indexName}</td>
            <td>${player.value}</td>
            <td class="actions">
                <a href="player/edit?id=${player.playerId}"><img src="https://cdn-icons-png.flaticon.com/16/1160/1160515.png" alt="Edit"></a>
                <a href="player/delete?id=${player.playerId}" onclick="return confirm('Are you sure?')"><img src="https://cdn-icons-png.flaticon.com/16/1214/1214428.png" alt="Delete"></a>
            </td>
        </tr>
    </c:forEach>
</table>

<footer>
    Số 8, Tôn Thất Thuyết, Cầu Giấy, Hà Nội
</footer>
</body>
</html>