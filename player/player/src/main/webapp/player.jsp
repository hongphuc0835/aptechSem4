<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Player List</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .form-container {
            margin: 20px 0;
        }
        .form-container input, .form-container select {
            padding: 5px;
            margin: 5px;
        }
        .form-container button {
            padding: 5px 10px;
            background-color: #d2691e;
            color: white;
            border: none;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #b3591a;
        }
    </style>
</head>
<body>
<h1>Player List</h1>

<div class="form-container">
    <form action="AddPlayerServlet" method="post">
        <label>Player name</label>
        <input type="text" name="playerName" required>

        <label>Player age</label>
        <input type="number" name="age" required>

        <label>Index name</label>
        <select name="indexName" required>
            <option value="speed">Speed</option>
            <option value="strength">Strength</option>
            <option value="accurate">Accurate</option>
        </select>

        <label>Value</label>
        <select name="value" required>
            <option value="90">90</option>
            <option value="1">1</option>
        </select>

        <button type="submit">Add</button>
    </form>
</div>

<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Player name</th>
        <th>Player age</th>
        <th>Index Name</th>
        <th>Value</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${playerIndices}" var="player" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${player.playerName}</td>
            <td>${player.age}</td>
            <td>${player.indexName}</td>
            <td>${player.value}</td>
        </tr>
    </c:forEach>
    <c:if test="${empty playerIndices}">
        <tr>
            <td colspan="5">No players found</td>
        </tr>
    </c:if>
    </tbody>
</table>
</body>
</html>