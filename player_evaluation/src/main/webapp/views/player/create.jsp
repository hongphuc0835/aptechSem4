<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Player</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Add Player</h2>

<div class="form-container">
    <form action="player/create" method="post">
        <div class="form-group-vertical">
            <label for="playerName">Player name</label>
            <input type="text" id="playerName" name="playerName" placeholder="Player name" required>
        </div>
        <div class="form-group-vertical">
            <label for="playerAge">Player age</label>
            <input type="text" id="playerAge" name="playerAge" placeholder="Player age" required>
        </div>
        <div class="form-group-vertical">
            <label for="indexName">Index name</label>
            <select id="indexName" name="indexName" required>
                <option value="">Select index</option>
                <option value="speed">speed</option>
                <option value="strength">strength</option>
                <option value="accurate">accurate</option>
            </select>
        </div>
        <div class="form-group-vertical">
            <label for="value">Value</label>
            <select id="value" name="value" required>
                <option value="">Select value</option>
                <option value="90">90</option>
                <option value="1">1</option>
            </select>
        </div>
        <button type="submit">Add Player</button>
    </form>
    <a href="player" class="back-link">Back to List</a>
</div>
</body>
</html>