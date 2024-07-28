<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<html>
<head>
    <title>Meals</title>
    <style>
        th, td{
            border: 1px solid;
            padding: 5px;
        }
    </style>
</head>
<body>
<a href="meals?action=create">Create meal</a><br><br>
<span>Calories limit per date: <b>${MealsUtil.CALORIES_PER_DATE}</b> </span>
<table style="width: 100%">
    <tr>
        <th>Date time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    <c:set var="formatter" scope="request" value="${DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')}"/>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td style="text-align: center">${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td style="text-align: center">${meal.calories}</td>
            <td style="text-align: center"><a href="meals?action=edit&id=${meal.id}">Edit</a></td>
            <td style="text-align: center;"><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
