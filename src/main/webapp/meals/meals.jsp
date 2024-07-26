<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table style="width: 100%; border: 1px solid black">
    <tr>
        <th>Date time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <tr style=background-color:${meal.excess ? 'orangered' : 'greenyellow'}>
            <td style="text-align: center">${meal.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
            <td>${meal.description}</td>
            <td style="text-align: center">${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
