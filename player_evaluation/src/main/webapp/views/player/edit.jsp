<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Player</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h2>Edit Player</h2>

<div class="form-container">
    <form action="player/edit" method="post">
        <input type="hidden" name="playerId" value="${player.playerId}">
        <div class="form-group-vertical">
            <label for="playerName">Player name</label>
            <input type="text" id="playerName" name="playerName" value="${player.name}" required>
        </div>
        <div class="form-group-vertical">
            <label for="playerAge">Player age</label>
            <input type="text" id="playerAge" name="playerAge" value="${player.age}" required>
        </div>
        <div class="form-group-vertical">
            <label for="indexName">Index name</label>
            <select id="indexName" name="indexName" required>
                <option value="speed" ${player.indexName == 'speed' ? 'selected' : ''}>speed</option>
                <option value="strength" ${player.indexName == 'strength' ? 'selected' : ''}>strength</option>
                <option value="accurate" ${player.indexName == 'accurate' ? 'selected' : ''}>accurate</option>
            </select>
        </div>
        <div class="form-group-vertical">
            <label for="value">Value</label>
            <select id="value" name="value" required>
                <option value="90" ${player.value == 90 ? 'selected' : ''}>90</option>
                <option value="1" ${player.value == 1 ? 'selected' : ''}>1</option>
            </select>
        </div>
        <button type="submit">Update Player</button>
    </form>
    <a href="player" class="back-link">Back to List</a>
</div>
</body>
</html>